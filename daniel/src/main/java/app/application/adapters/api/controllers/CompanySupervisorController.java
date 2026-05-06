package app.application.adapters.api.controllers;

import app.application.adapters.api.request.TransferApprovalRequest;
import app.application.adapters.api.request.TransferRejectionRequest;
import app.application.adapters.api.request.CreateOperativeUserRequest;
import app.application.adapters.api.request.BlockOperativeUserRequest;
import app.application.adapters.api.response.TransferResponse;
import app.application.adapters.api.response.UserResponse;
import app.application.usecases.CompanySupervisorUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-supervisor")
@Validated
public class CompanySupervisorController {
    private final CompanySupervisorUseCase useCase;

    public CompanySupervisorController(CompanySupervisorUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account")
    public ResponseEntity<Optional<BankAccount>> getCompanyAccount(@RequestParam long requestingUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getCompanyAccount(requestingUserId));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccount> getCompanyAccountDetail(@RequestParam long requestingUserId, @PathVariable String accountNumber) throws BusinessException {
        return ResponseEntity.ok(useCase.getCompanyAccountDetail(requestingUserId, accountNumber));
    }

    @GetMapping("/transfers/pending")
    public ResponseEntity<List<TransferResponse>> getPendingTransfersForApproval(@RequestParam long requestingUserId) throws BusinessException {
        List<Transfer> transfers = useCase.getPendingTransfersForApproval(requestingUserId);
        return ResponseEntity.ok(transfers.stream().map(this::toTransferResponse).toList());
    }

    @GetMapping("/transfer/{transferId}")
    public ResponseEntity<TransferResponse> getTransferDetail(@RequestParam long requestingUserId, @PathVariable long transferId) throws BusinessException {
        Transfer transfer = useCase.getTransferDetail(requestingUserId, transferId);
        return ResponseEntity.ok(toTransferResponse(transfer));
    }

    @PostMapping("/transfer/{transferId}/approve")
    public ResponseEntity<TransferResponse> approveTransfer(
            @RequestParam long requestingUserId, 
            @PathVariable long transferId, 
            @Valid @RequestBody(required = false) TransferApprovalRequest request) throws BusinessException {
        String approvalNotes = request != null ? request.getApprovalNotes() : null;
        Transfer transfer = useCase.approveTransfer(requestingUserId, transferId, approvalNotes);
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

    private UserResponse toUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setIdentificationId(user.getIdentificationId());
        response.setIdentificationType(user.getIdentificationType());
        response.setPhone(user.getPhone());
        response.setAddress(user.getAddress());
        response.setStatus(user.getStatus().toString());
        response.setRole(user.getRole().toString());
        response.setCreatedAt(user.getCreatedAt());
        response.setLastLogin(user.getLastLogin());
        return response;
    }
}
