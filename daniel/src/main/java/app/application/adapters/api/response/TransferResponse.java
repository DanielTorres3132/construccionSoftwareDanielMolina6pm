package app.application.adapters.api.response;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO de respuesta para transferencias bancarias.
 * 
 * Transporta la información de una transferencia hacia la respuesta HTTP.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponse {

    private Long id;

    private String sourceAccount;

    private String destinationAccount;

    private BigDecimal amount;

    private TransferStatus transferStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalDate;

    private Long creatorUserId;

    private Long approverUserId;

    private String rejectionReason;

    /**
     * Convierte un modelo de dominio Transfer a respuesta.
     * 
     * @param transfer Transferencia del dominio
     * @return DTO de respuesta
     */
    public static TransferResponse fromTransfer(Transfer transfer) {
        return TransferResponse.builder()
                .id(transfer.getId())
                .sourceAccount(transfer.getSourceAccount())
                .destinationAccount(transfer.getDestinationAccount())
                .amount(transfer.getAmount())
                .transferStatus(transfer.getTransferStatus())
                .creationDate(transfer.getCreationDate())
                .approvalDate(transfer.getApprovalDate())
                .creatorUserId(transfer.getCreatorUserId())
                .approverUserId(transfer.getApproverUserId())
                .rejectionReason(transfer.getRejectionReason())
                .build();
    }
}
