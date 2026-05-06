package app.application.adapters.api.controllers;

import app.application.usecases.CompanySupervisorUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<Transfer>> getPendingTransfersForApproval(@RequestParam long requestingUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getPendingTransfersForApproval(requestingUserId));
    }

    @GetMapping("/transfer/{transferId}")
    public ResponseEntity<Transfer> getTransferDetail(@RequestParam long requestingUserId, @PathVariable long transferId) throws BusinessException {
        return ResponseEntity.ok(useCase.getTransferDetail(requestingUserId, transferId));
    }

    @PostMapping("/transfer/{transferId}/approve")
    public ResponseEntity<Transfer> approveTransfer(@RequestParam long requestingUserId, @PathVariable long transferId, @RequestParam(required = false) String approvalNotes) throws BusinessException {
        return ResponseEntity.ok(useCase.approveTransfer(requestingUserId, transferId, approvalNotes));
    }

    @PostMapping("/transfer/{transferId}/reject")
    public ResponseEntity<Transfer> rejectTransfer(@RequestParam long requestingUserId, @PathVariable long transferId, @RequestParam String rejectionReason) throws BusinessException {
        return ResponseEntity.ok(useCase.rejectTransfer(requestingUserId, transferId, rejectionReason));
    }

    @PostMapping("/operative-user")
    public ResponseEntity<User> createOperativeUser(
            @RequestParam long requestingUserId,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String identificationId,
            @RequestParam String identificationType,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String password) throws BusinessException {
        return ResponseEntity.ok(useCase.createOperativeUser(requestingUserId, username, email, firstName, lastName, identificationId, identificationType, phone, address, password));
    }

    @PostMapping("/operative-user/{operativeUserId}/activate")
    public ResponseEntity<Void> activateOperativeUser(@RequestParam long requestingUserId, @PathVariable long operativeUserId) throws BusinessException {
        useCase.activateOperativeUser(requestingUserId, operativeUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/operative-user/{operativeUserId}/deactivate")
    public ResponseEntity<Void> deactivateOperativeUser(@RequestParam long requestingUserId, @PathVariable long operativeUserId) throws BusinessException {
        useCase.deactivateOperativeUser(requestingUserId, operativeUserId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/operative-user/{operativeUserId}/block")
    public ResponseEntity<Void> blockOperativeUser(@RequestParam long requestingUserId, @PathVariable long operativeUserId, @RequestParam String blockReason) throws BusinessException {
        useCase.blockOperativeUser(requestingUserId, operativeUserId, blockReason);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/operative-user/{operativeUserId}")
    public ResponseEntity<User> getOperativeUserDetail(@RequestParam long requestingUserId, @PathVariable long operativeUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getOperativeUserDetail(requestingUserId, operativeUserId));
    }
}
