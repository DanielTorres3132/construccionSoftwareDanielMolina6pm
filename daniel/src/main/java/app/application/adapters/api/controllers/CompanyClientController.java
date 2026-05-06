package app.application.adapters.api.controllers;

import app.application.usecases.CompanyClientUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Loan.Loan;
import app.domain.models.Transfer.Transfer;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/company-client")
@Validated
public class CompanyClientController {
    private final CompanyClientUseCase useCase;

    public CompanyClientController(CompanyClientUseCase useCase) {
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

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<Loan> getCompanyLoan(@RequestParam long requestingUserId, @PathVariable long loanId) throws BusinessException {
        return ResponseEntity.ok(useCase.getCompanyLoan(requestingUserId, loanId));
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getCompanyTransferHistory(@RequestParam long requestingUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getCompanyTransferHistory(requestingUserId));
    }

    @PostMapping("/loan-request")
    public ResponseEntity<Loan> createLoanRequest(
            @RequestParam long requestingUserId,
            @RequestParam String loanType,
            @RequestParam BigDecimal requestedAmount,
            @RequestParam int termMonths) throws BusinessException {
        Loan loan = useCase.createLoanRequest(requestingUserId, app.domain.models.Loan.enums.LoanType.valueOf(loanType), requestedAmount, termMonths);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> createTransfer(
            @RequestParam long requestingUserId,
            @RequestParam String sourceAccount,
            @RequestParam String destinationAccount,
            @RequestParam BigDecimal amount) throws BusinessException {
        Transfer transfer = useCase.createTransfer(requestingUserId, sourceAccount, destinationAccount, amount);
        return ResponseEntity.ok(transfer);
    }
}
