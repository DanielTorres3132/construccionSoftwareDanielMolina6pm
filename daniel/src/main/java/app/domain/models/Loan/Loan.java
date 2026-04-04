package app.domain.models.Loan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.domain.models.Loan.enums.LoanStatus;
import app.domain.models.Loan.enums.LoanType;

@Setter
@Getter
@NoArgsConstructor
public class Loan { 
    private long id; 
    private LoanType loanType; 
    private String applicantClientId; 
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount; 
    private BigDecimal interestRate; 
    private int termMonths; 
    private LoanStatus loanStatus; 
    private LocalDate requestDate; 
    private LocalDate approvalDate; 
    private LocalDate disbursementDate; 
    private String disbursementAccount; 
    private long approverAnalystId; 
    private String rejectionReason; 
}