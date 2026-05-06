package app.application.adapters.api.controllers;

import app.application.usecases.CompanyEmployeeUseCase;
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
    public ResponseEntity<Loan> getCompanyLoan(@RequestParam long requestingUserId, @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getCompanyLoan(requestingUserId, loanId);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getCompanyTransferHistory(@RequestParam long requestingUserId) throws BusinessException {
        List<Transfer> transfers = useCase.getCompanyTransferHistory(requestingUserId);
        return ResponseEntity.ok(transfers);
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

    @PostMapping("/loan/{loanId}/approve")
    public ResponseEntity<Loan> approveLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @RequestParam BigDecimal approvedAmount,
            @RequestParam BigDecimal interestRate) throws BusinessException {
        Loan loan = useCase.approveLoan(requestingUserId, loanId, approvedAmount, interestRate);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> createTransfer(
            @RequestParam long requestingUserId,
            @RequestParam String sourceAccountNumber,
            @RequestParam String destinationAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String description) throws BusinessException {
        Transfer transfer = useCase.createTransfer(requestingUserId, sourceAccountNumber, destinationAccountNumber, amount, description);
        return ResponseEntity.ok(transfer);
    }
}
