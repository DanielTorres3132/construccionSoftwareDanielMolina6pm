package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.models.Loan.enums.LoanType;
import app.domain.ports.LoanRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class LoanCreateRequestService {
    private final LoanRepositoryPort loanRepositoryPort;

    public LoanCreateRequestService(LoanRepositoryPort loanRepositoryPort) {
        this.loanRepositoryPort = loanRepositoryPort;
    }

    public Loan execute(String applicantClientId, LoanType loanType, BigDecimal requestedAmount, int termMonths) {
        Loan loan = new Loan();
        loan.setApplicantClientId(applicantClientId);
        loan.setLoanType(loanType);
        loan.setRequestedAmount(requestedAmount);
        loan.setTermMonths(termMonths);
        loan.setLoanStatus(LoanStatus.UNDER_STUDY);
        loan.setRequestDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }
}
