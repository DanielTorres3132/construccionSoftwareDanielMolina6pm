package app.domain.models.Transfer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import app.domain.models.Transfer.enums.TransferStatus;

@Setter
@Getter
@NoArgsConstructor
public class Transfer { 
    private long id;
    private String sourceAccount; 
    private String destinationAccount;
    private BigDecimal amount;
    private LocalDateTime creationDate; 
    private LocalDateTime approvalDate; 
    private TransferStatus transferStatus; 
    private long creatorUserId; 
    private long approverUserId; 
    private String rejectionReason; 
}