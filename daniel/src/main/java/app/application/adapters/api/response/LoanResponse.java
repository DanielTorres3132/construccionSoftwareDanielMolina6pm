package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class LoanResponse {
    private long id;
    private String applicantClientId;
    private String loanType;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    private String loanStatus;
    private LocalDate requestDate;
    private LocalDate approvalDate;
    private LocalDate disbursementDate;
    private String disbursementAccount;
    private String rejectionReason;
}