package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.LoanRequestCreateRequest;
import app.application.adapters.api.request.TransferCreateRequest;
import app.application.adapters.api.response.BankAccountResponse;
import app.application.adapters.api.response.LoanResponse;
import app.application.adapters.api.response.TransferResponse;
import app.application.usecases.NaturalPersonClientUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanType;
import app.domain.models.Transfer.Transfer;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/natural-person-client")
@Validated
public class NaturalPersonClientController {

    @Autowired
    private NaturalPersonClientUseCase useCase;

    public NaturalPersonClientController(NaturalPersonClientUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account")
    public ResponseEntity<BankAccountResponse> getOwnAccount(@RequestParam long requestingUserId) throws BusinessException {
        Optional<BankAccount> account = useCase.getOwnAccount(requestingUserId);
        return account.map(a -> ResponseEntity.ok(toBankAccountResponse(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getOwnAccountDetail(
            @RequestParam long requestingUserId,
            @PathVariable String accountNumber) throws BusinessException {
        BankAccount account = useCase.getOwnAccountDetail(requestingUserId, accountNumber);
        return ResponseEntity.ok(toBankAccountResponse(account));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanResponse> getOwnLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getOwnLoan(requestingUserId, loanId);
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<TransferResponse>> getOwnTransferHistory(@RequestParam long requestingUserId) throws BusinessException {
        List<Transfer> transfers = useCase.getOwnTransferHistory(requestingUserId);
        return ResponseEntity.ok(transfers.stream().map(this::toTransferResponse).toList());
    }

    @PostMapping("/loan-request")
    public ResponseEntity<LoanResponse> createLoanRequest(
            @RequestParam long requestingUserId,
            @Valid @RequestBody LoanRequestCreateRequest request) throws BusinessException {
        Loan loan = useCase.createLoanRequest(
                requestingUserId,
                LoanType.valueOf(request.getLoanType()),
                request.getRequestedAmount(),
                request.getTermMonths());
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> createTransfer(
            @RequestParam long requestingUserId,
            @Valid @RequestBody TransferCreateRequest request) throws BusinessException {
        Transfer transfer = useCase.createTransfer(
                requestingUserId,
                request.getSourceAccount(),
                request.getDestinationAccount(),
                request.getAmount());
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    // ─── Mappers ──────────────────────────────────────────────────────────────

    private static BankAccountResponse toBankAccountResponse(BankAccount account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType().toString());
        response.setHolderId(account.getHolderId());
        response.setCurrentBalance(account.getCurrentBalance());
        response.setCurrency(account.getCurrency().toString());
        response.setAccountStatus(account.getAccountStatus().toString());
        response.setOpeningDate(account.getOpeningDate());
        return response;
    }

    private static LoanResponse toLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setApplicantClientId(loan.getApplicantClientId());
        response.setLoanType(loan.getLoanType().toString());
        response.setRequestedAmount(loan.getRequestedAmount());
        response.setApprovedAmount(loan.getApprovedAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTermMonths(loan.getTermMonths());
        response.setLoanStatus(loan.getLoanStatus().toString());
        response.setRequestDate(loan.getRequestDate());
        response.setApprovalDate(loan.getApprovalDate());
        response.setDisbursementDate(loan.getDisbursementDate());
        response.setDisbursementAccount(loan.getDisbursementAccount());
        response.setRejectionReason(loan.getRejectionReason());
        return response;
    }

    private static TransferResponse toTransferResponse(Transfer transfer) {
        TransferResponse response = new TransferResponse();
        response.setId(transfer.getId());
        response.setSourceAccount(transfer.getSourceAccount());
        response.setDestinationAccount(transfer.getDestinationAccount());
        response.setAmount(transfer.getAmount());
        response.setTransferStatus(transfer.getTransferStatus().toString());
        response.setCreationDate(transfer.getCreationDate());
        response.setApprovalDate(transfer.getApprovalDate());
        response.setCreatorUserId(transfer.getCreatorUserId());
        response.setApproverUserId(transfer.getApproverUserId());
        response.setRejectionReason(transfer.getRejectionReason());
        return response;
    }
}