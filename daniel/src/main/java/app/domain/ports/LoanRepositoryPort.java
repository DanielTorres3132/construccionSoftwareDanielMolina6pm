package app.domain.ports;

import app.domain.models.Loan.Loan;
import java.util.Optional;

public interface LoanRepositoryPort {
     Loan save(Loan loan);
    Optional<Loan> findById(long id);
    Optional<Loan> findByApplicantClientId(String applicantClientId);

}
