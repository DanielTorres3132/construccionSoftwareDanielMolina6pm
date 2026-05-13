package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.ports.LoanRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class LoanRejectService {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LoanGetOrThrowService getOrThrowService;

    public LoanRejectService(LoanRepositoryPort loanRepositoryPort, LoanGetOrThrowService getOrThrowService) {
        this.loanRepositoryPort = loanRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public Loan execute(long loanId, long approverAnalystId, String rejectionReason) {
        Loan loan = getOrThrowService.execute(loanId);
        if (loan.getLoanStatus() != LoanStatus.UNDER_STUDY) {
            throw new IllegalStateException("Solo se pueden rechazar préstamos en estado UNDER_STUDY.");
        }
        loan.setLoanStatus(LoanStatus.REJECTED);
        loan.setApproverAnalystId(approverAnalystId);
        loan.setRejectionReason(rejectionReason);
        return loanRepositoryPort.save(loan);
    }
}
