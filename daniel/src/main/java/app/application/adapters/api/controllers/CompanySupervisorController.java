package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.BlockOperativeUserRequest;
import app.application.adapters.api.request.CreateOperativeUserRequest;
import app.application.adapters.api.request.TransferApprovalRequest;
import app.application.adapters.api.request.TransferRejectionRequest;
import app.application.adapters.api.response.BankAccountResponse;
import app.application.adapters.api.response.TransferResponse;
import app.application.adapters.api.response.UserResponse;
import app.application.usecases.CompanySupervisorUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-supervisor")
@Validated
public class CompanySupervisorController {

    @Autowired
    private CompanySupervisorUseCase useCase;

    public CompanySupervisorController(CompanySupervisorUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account")
    public ResponseEntity<BankAccountResponse> getCompanyAccount(@RequestParam long requestingUserId) throws BusinessException {
        Optional<BankAccount> account = useCase.getCompanyAccount(requestingUserId);
        return account.map(a -> ResponseEntity.ok(toBankAccountResponse(a)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getCompanyAccountDetail(
            @RequestParam long requestingUserId,
            @PathVariable String accountNumber) throws BusinessException {
        BankAccount account = useCase.getCompanyAccountDetail(requestingUserId, accountNumber);
        return ResponseEntity.ok(toBankAccountResponse(account));
    }

    @GetMapping("/transfers/pending")
    public ResponseEntity<List<TransferResponse>> getPendingTransfers(@RequestParam long requestingUserId) throws BusinessException {
        List<Transfer> transfers = useCase.getPendingTransfersForApproval(requestingUserId);
        return ResponseEntity.ok(transfers.stream().map(CompanySupervisorController::toTransferResponse).toList());
    }

    @GetMapping("/transfer/{transferId}")
    public ResponseEntity<TransferResponse> getTransferDetail(
            @RequestParam long requestingUserId,
            @PathVariable long transferId) throws BusinessException {
        Transfer transfer = useCase.getTransferDetail(requestingUserId, transferId);
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    @PostMapping("/transfer/{transferId}/approve")
    public ResponseEntity<TransferResponse> approveTransfer(
            @RequestParam long requestingUserId,
            @PathVariable long transferId,
            @RequestBody(required = false) TransferApprovalRequest request) throws BusinessException {
        String notes = request != null ? request.getApprovalNotes() : null;
        Transfer transfer = useCase.approveTransfer(requestingUserId, transferId, notes);
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    @PostMapping("/transfer/{transferId}/reject")
    public ResponseEntity<TransferResponse> rejectTransfer(
            @RequestParam long requestingUserId,
            @PathVariable long transferId,
            @Valid @RequestBody TransferRejectionRequest request) throws BusinessException {
        Transfer transfer = useCase.rejectTransfer(requestingUserId, transferId, request.getRejectionReason());
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    @PostMapping("/operative-user")
    public ResponseEntity<UserResponse> createOperativeUser(
            @RequestParam long requestingUserId,
            @Valid @RequestBody CreateOperativeUserRequest request) throws BusinessException {
        User user = useCase.createOperativeUser(
                requestingUserId,
                request.getUsername(),
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getIdentificationId(),
                request.getIdentificationType(),
                request.getPhone(),
                request.getAddress(),
                request.getPassword());
        return ResponseEntity.ok(toUserResponse(user));
    }

    @PostMapping("/operative-user/{operativeUserId}/activate")
    public ResponseEntity<Void> activateOperativeUser(
            @RequestParam long requestingUserId,
            @PathVariable long operativeUserId) throws BusinessException {
        useCase.activateOperativeUser(requestingUserId, operativeUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/operative-user/{operativeUserId}/deactivate")
    public ResponseEntity<Void> deactivateOperativeUser(
            @RequestParam long requestingUserId,
            @PathVariable long operativeUserId) throws BusinessException {
        useCase.deactivateOperativeUser(requestingUserId, operativeUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/operative-user/{operativeUserId}/block")
    public ResponseEntity<Void> blockOperativeUser(
            @RequestParam long requestingUserId,
            @PathVariable long operativeUserId,
            @Valid @RequestBody BlockOperativeUserRequest request) throws BusinessException {
        useCase.blockOperativeUser(requestingUserId, operativeUserId, request.getBlockReason());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/operative-user/{operativeUserId}")
    public ResponseEntity<UserResponse> getOperativeUserDetail(
            @RequestParam long requestingUserId,
            @PathVariable long operativeUserId) throws BusinessException {
        User user = useCase.getOperativeUserDetail(requestingUserId, operativeUserId);
        return ResponseEntity.ok(toUserResponse(user));
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

    private static UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFullName(user.getFullName());
        response.setIdentificationId(user.getIdentificationId());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setUserName(user.getUserName());
        response.setSystemRole(user.getSystemRole().toString());
        response.setUserStatus(user.getUserStatus().toString());
        return response;
    }
}