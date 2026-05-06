package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponse {
    private Long id;
    private Long clientId;
    private String loanType;
    private BigDecimal requestedAmount;
    private BigDecimal approvedAmount;
    private BigDecimal interestRate;
    private int termMonths;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private String rejectionReason;
}
