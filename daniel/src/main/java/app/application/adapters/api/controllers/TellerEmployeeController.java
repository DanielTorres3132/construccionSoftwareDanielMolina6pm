package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.OpenAccountForClientRequest;
import app.application.adapters.api.request.OpenAccountRequest;
import app.application.adapters.api.response.BankAccountResponse;
import app.application.usecases.TellerEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/teller-employee")
@Validated
public class TellerEmployeeController {

    @Autowired
    private TellerEmployeeUseCase useCase;

    public TellerEmployeeController(TellerEmployeeUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account/{accountNumber}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(
            @RequestParam long requestingUserId,
            @PathVariable String accountNumber) throws BusinessException {
        return ResponseEntity.ok(useCase.getAccountBalance(requestingUserId, accountNumber));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccountResponse> getAccountDetail(
            @RequestParam long requestingUserId,
            @PathVariable String accountNumber) throws BusinessException {
        BankAccount account = useCase.getAccountDetail(requestingUserId, accountNumber);
        return ResponseEntity.ok(toBankAccountResponse(account));
    }

    @PostMapping("/account/open")
    public ResponseEntity<BankAccountResponse> openAccount(
            @RequestParam long requestingUserId,
            @Valid @RequestBody OpenAccountRequest request) throws BusinessException {
        BankAccount account = useCase.openAccount(
                requestingUserId,
                request.getClientIdentificationId(),
                request.getAccountType(),
                request.getCurrency());
        return ResponseEntity.ok(toBankAccountResponse(account));
    }

    @PostMapping("/account/open-for-client")
    public ResponseEntity<BankAccountResponse> openAccountForClient(
            @RequestParam long requestingUserId,
            @Valid @RequestBody OpenAccountForClientRequest request) throws BusinessException {
        BankAccount account = useCase.openAccountForClient(
                requestingUserId,
                request.getClientUserId(),
                request.getAccountType(),
                request.getCurrency());
        return ResponseEntity.ok(toBankAccountResponse(account));
    }

    // ─── Mapper ───────────────────────────────────────────────────────────────

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
}