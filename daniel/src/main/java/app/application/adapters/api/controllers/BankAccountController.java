package app.application.adapters.api.controllers;

import app.application.usecases.OpenBankAccountUseCase;
import app.application.adapters.api.request.OpenBankAccountRequest;
import app.application.adapters.api.response.BankAccountResponse;
import app.domain.models.Account.BankAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/bank-accounts")
public class BankAccountController {

    private final OpenBankAccountUseCase openBankAccountUseCase;

    @Autowired
    public BankAccountController(OpenBankAccountUseCase openBankAccountUseCase) {
        this.openBankAccountUseCase = openBankAccountUseCase;
    }

    @PostMapping("/open")
    public ResponseEntity<BankAccountResponse> openBankAccount(
            @Valid @RequestBody OpenBankAccountRequest request) {

        BankAccount createdAccount = openBankAccountUseCase.execute(request);

        BankAccountResponse response = BankAccountResponse.fromBankAccount(createdAccount);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<BankAccountResponse> getBalance(@PathVariable String accountNumber) {
        return ResponseEntity.ok(new BankAccountResponse());
    }

    @PutMapping("/{accountNumber}/block")
    public ResponseEntity<BankAccountResponse> blockAccount(@PathVariable String accountNumber) {
        return ResponseEntity.ok(new BankAccountResponse());
    }
}