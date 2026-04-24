package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.ports.LoanRepositoryPort;

public class LoanGetOrThrowService {
    private final LoanRepositoryPort loanRepositoryPort;

    public LoanGetOrThrowService(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    public Loan execute(long loanId) {
        return loanRepositoryPort.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el préstamo con id: " + loanId));
    }
}
