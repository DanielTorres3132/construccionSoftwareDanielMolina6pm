package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.ports.LoanRepositoryPort;
import java.time.LocalDate;

public class LoanDisburseService {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LoanGetOrThrowService getOrThrowService;

    public LoanDisburseService(LoanRepositoryPort loanRepositoryPort, LoanGetOrThrowService getOrThrowService) {
        this.loanRepositoryPort = loanRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public Loan execute(long loanId, String disbursementAccount) {
        Loan loan = getOrThrowService.execute(loanId);
        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Solo se pueden desembolsar préstamos en estado APPROVED.");
        }
        loan.setLoanStatus(LoanStatus.DISBURSED);
        loan.setDisbursementAccount(disbursementAccount);
        loan.setDisbursementDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }
}
