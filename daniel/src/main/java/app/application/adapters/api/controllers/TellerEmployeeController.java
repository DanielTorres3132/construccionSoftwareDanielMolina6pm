package app.application.adapters.api.controllers;

import app.application.adapters.api.request.OpenAccountRequest;
import app.application.adapters.api.request.OpenAccountForClientRequest;
import app.application.adapters.api.response.BankAccountResponse;
import app.application.usecases.TellerEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/teller-employee")
@Validated
public class TellerEmployeeController {
    private final TellerEmployeeUseCase useCase;

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

    private BankAccountResponse toBankAccountResponse(BankAccount account) {
        BankAccountResponse response = new BankAccountResponse();
        response.setId(account.getId());
        response.setAccountNumber(account.getAccountNumber());
        response.setAccountType(account.getAccountType().toString());
        response.setCurrency(account.getCurrency().toString());
        response.setBalance(account.getBalance());
        response.setStatus(account.getStatus().toString());
        response.setOpenedDate(account.getOpenedDate());
        response.setClientId(account.getClientId());
        return response;
    }
}
