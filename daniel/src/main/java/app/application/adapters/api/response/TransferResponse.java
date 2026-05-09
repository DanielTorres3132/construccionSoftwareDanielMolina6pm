package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransferResponse {
    private long id;
    private String sourceAccount;
    private String destinationAccount;
    private BigDecimal amount;
    private String transferStatus;
    private LocalDateTime creationDate;
    private LocalDateTime approvalDate;
    private long creatorUserId;
    private long approverUserId;
    private String rejectionReason;
}