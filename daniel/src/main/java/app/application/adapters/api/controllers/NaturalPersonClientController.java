package app.application.adapters.api.controllers;

import app.application.usecases.NaturalPersonClientUseCase;
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
@RequestMapping("/api/natural-person-client")
@Validated
public class NaturalPersonClientController {
    private final NaturalPersonClientUseCase useCase;

    public NaturalPersonClientController(NaturalPersonClientUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/account")
    public ResponseEntity<Optional<BankAccount>> getOwnAccount(@RequestParam long requestingUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getOwnAccount(requestingUserId));
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<BankAccount> getOwnAccountDetail(@RequestParam long requestingUserId, @PathVariable String accountNumber) throws BusinessException {
        return ResponseEntity.ok(useCase.getOwnAccountDetail(requestingUserId, accountNumber));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<Loan> getOwnLoan(@RequestParam long requestingUserId, @PathVariable long loanId) throws BusinessException {
        return ResponseEntity.ok(useCase.getOwnLoan(requestingUserId, loanId));
    }

    @GetMapping("/transfers")
    public ResponseEntity<List<Transfer>> getOwnTransferHistory(@RequestParam long requestingUserId) throws BusinessException {
        return ResponseEntity.ok(useCase.getOwnTransferHistory(requestingUserId));
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
