package app.application.adapters.api.controllers;

import app.application.usecases.TellerEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<BankAccount> getAccountDetail(
            @RequestParam long requestingUserId,
            @PathVariable String accountNumber) throws BusinessException {
        return ResponseEntity.ok(useCase.getAccountDetail(requestingUserId, accountNumber));
    }

    @PostMapping("/account/open")
    public ResponseEntity<BankAccount> openAccount(
            @RequestParam long requestingUserId,
            @RequestParam String clientIdentificationId,
            @RequestParam AccountType accountType,
            @RequestParam Currency currency) throws BusinessException {
        return ResponseEntity.ok(useCase.openAccount(requestingUserId, clientIdentificationId, accountType, currency));
    }

    @PostMapping("/account/open-for-client")
    public ResponseEntity<BankAccount> openAccountForClient(
            @RequestParam long requestingUserId,
            @RequestParam long clientUserId,
            @RequestParam AccountType accountType,
            @RequestParam Currency currency) throws BusinessException {
        return ResponseEntity.ok(useCase.openAccountForClient(requestingUserId, clientUserId, accountType, currency));
    }
}
