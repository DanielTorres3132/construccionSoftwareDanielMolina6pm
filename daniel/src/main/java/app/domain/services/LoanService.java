package app.domain.services;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.models.Loan.enums.LoanType;
import app.domain.ports.LoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
 
import java.math.BigDecimal;
import java.time.LocalDate;
 
@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepositoryPort loanRepositoryPort;

    // Crear solicitud de préstamo
    public Loan createLoanRequest(String applicantClientId, LoanType loanType, BigDecimal requestedAmount, int termMonths) {
        Loan loan = new Loan();
        loan.setApplicantClientId(applicantClientId);
        loan.setLoanType(loanType);
        loan.setRequestedAmount(requestedAmount);
        loan.setTermMonths(termMonths);
        loan.setLoanStatus(LoanStatus.UNDER_STUDY);
        loan.setRequestDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }
    
    // Aprobar solicitud de préstamo
    public Loan approveLoan(long loanId, long approverAnalystId,
                            BigDecimal approvedAmount, BigDecimal interestRate) {
        Loan loan = getLoanOrThrow(loanId);
 
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

    // Rechazar solicitud de préstamo
    public Loan rejectLoan(long loanId, long approverAnalystId, String rejectionReason) {
        Loan loan = getLoanOrThrow(loanId);
 
        if (loan.getLoanStatus() != LoanStatus.UNDER_STUDY) {
            throw new IllegalStateException("Solo se pueden rechazar préstamos en estado UNDER_STUDY.");
        }
 
        loan.setLoanStatus(LoanStatus.REJECTED);
        loan.setApproverAnalystId(approverAnalystId);
        loan.setRejectionReason(rejectionReason);
        return loanRepositoryPort.save(loan);
    }
 
    // Desembolsar préstamo
    public Loan disburseLoan(long loanId, String disbursementAccount) {
        Loan loan = getLoanOrThrow(loanId);
 
        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Solo se pueden desembolsar préstamos en estado APPROVED.");
        }
 
        loan.setLoanStatus(LoanStatus.DISBURSED);
        loan.setDisbursementAccount(disbursementAccount);
        loan.setDisbursementDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }

    private Loan getLoanOrThrow(long loanId) {
        return loanRepositoryPort.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el préstamo con id: " + loanId));
    }
 

}
