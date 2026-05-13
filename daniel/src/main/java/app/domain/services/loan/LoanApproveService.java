package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.ports.LoanRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class LoanApproveService {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LoanGetOrThrowService getOrThrowService;

    public LoanApproveService(LoanRepositoryPort loanRepositoryPort, LoanGetOrThrowService getOrThrowService) {
        this.loanRepositoryPort = loanRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public Loan execute(long loanId, long approverAnalystId, BigDecimal approvedAmount, BigDecimal interestRate) {
        Loan loan = getOrThrowService.execute(loanId);
        if (loan.getLoanStatus() != LoanStatus.UNDER_STUDY) {
            throw new IllegalStateException("Solo se pueden aprobar préstamos en estado UNDER_STUDY.");
        }
        loan.setLoanStatus(LoanStatus.APPROVED);
        loan.setApproverAnalystId(approverAnalystId);
        loan.setApprovedAmount(approvedAmount);
        loan.setInterestRate(interestRate);
        loan.setApprovalDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }
}
