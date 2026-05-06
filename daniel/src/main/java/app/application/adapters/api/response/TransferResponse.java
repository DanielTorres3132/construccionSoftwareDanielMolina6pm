package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferResponse {
    private Long id;
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String status;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime approvedAt;
    private String approvalNotes;
    private String rejectionReason;
}
