package app.application.adapters.api.controllers;

import app.application.adapters.api.request.LoanRequestCreateRequest;
import app.application.adapters.api.request.LoanApprovalRequest;
import app.application.adapters.api.request.TransferCreateRequest;
import app.application.adapters.api.response.LoanResponse;
import app.application.adapters.api.response.TransferResponse;
import app.application.usecases.CompanyEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Loan.Loan;
import app.domain.models.Transfer.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-employee")
@Validated
public class CompanyEmployeeController {
    private final CompanyEmployeeUseCase useCase;

    public CompanyEmployeeController(CompanyEmployeeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account")
    public ResponseEntity<BankAccount> getCompanyAccount(@RequestParam long requestingUserId) throws BusinessException {
        Optional<BankAccount> account = useCase.getCompanyAccount(requestingUserId);
        return account.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccount> getCompanyAccountDetail(@RequestParam long requestingUserId, @PathVariable String accountNumber) throws BusinessException {
        BankAccount account = useCase.getCompanyAccountDetail(requestingUserId, accountNumber);
        return ResponseEntity.ok(account);
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanResponse> getCompanyLoan(@RequestParam long requestingUserId, @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getCompanyLoan(requestingUserId, loanId);
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<TransferResponse>> getCompanyTransferHistory(@RequestParam long requestingUserId) throws BusinessException {
        List<Transfer> transfers = useCase.getCompanyTransferHistory(requestingUserId);
        return ResponseEntity.ok(transfers.stream().map(this::toTransferResponse).toList());
    }

    @PostMapping("/loan-request")
    public ResponseEntity<LoanResponse> createLoanRequest(
            @RequestParam long requestingUserId,
            @Valid @RequestBody LoanRequestCreateRequest request) throws BusinessException {
        Loan loan = useCase.createLoanRequest(
                requestingUserId, 
                app.domain.models.Loan.enums.LoanType.valueOf(request.getLoanType()), 
                request.getRequestedAmount(), 
                request.getTermMonths());
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @PostMapping("/loan/{loanId}/approve")
    public ResponseEntity<LoanResponse> approveLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @Valid @RequestBody LoanApprovalRequest request) throws BusinessException {
        Loan loan = useCase.approveLoan(requestingUserId, loanId, request.getApprovedAmount(), request.getInterestRate());
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
                request.getAmount(),
                request.getDescription());
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    private LoanResponse toLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setClientId(loan.getClientId());
        response.setLoanType(loan.getLoanType().toString());
        response.setRequestedAmount(loan.getRequestedAmount());
        response.setApprovedAmount(loan.getApprovedAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTermMonths(loan.getTermMonths());
        response.setStatus(loan.getStatus().toString());
        response.setCreatedAt(loan.getCreatedAt());
        response.setApprovedAt(loan.getApprovedAt());
        response.setRejectionReason(loan.getRejectionReason());
        return response;
    }

    private TransferResponse toTransferResponse(Transfer transfer) {
        TransferResponse response = new TransferResponse();
        response.setId(transfer.getId());
        response.setSourceAccount(transfer.getSourceAccount());
        response.setDestinationAccount(transfer.getDestinationAccount());
        response.setAmount(transfer.getAmount());
        response.setStatus(transfer.getStatus().toString());
        response.setDescription(transfer.getDescription());
        response.setCreatedAt(transfer.getCreatedAt());
        response.setApprovedAt(transfer.getApprovedAt());
        response.setApprovalNotes(transfer.getApprovalNotes());
        response.setRejectionReason(transfer.getRejectionReason());
        return response;
    }
}
