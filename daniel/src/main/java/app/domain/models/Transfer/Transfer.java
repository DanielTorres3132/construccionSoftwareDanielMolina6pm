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
public class Transfer { //transferencia
    private long id;
    private String sourceAccount; //cuenta origen
    private String destinationAccount; //cuenta destino
    private BigDecimal amount; //monto
    private LocalDateTime creationDate; //fecha de creacion
    private LocalDateTime approvalDate; //fecha de aprobacion
    private TransferStatus transferStatus; //estado de la transferencia
    private long creatorUserId; //id del usuario creador
    private long approverUserId; //id del usuario aprobador
    private String rejectionReason; //razon de rechazo
}