package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import app.domain.models.Transfer.enums.TransferStatus;

@Entity
@Table(name = "transfers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_account", nullable = false)
    private String sourceAccount;

    @Column(name = "destination_account", nullable = false)
    private String destinationAccount;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "approval_date")
    private LocalDateTime approvalDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "transfer_status", nullable = false)
    private TransferStatus transferStatus;

    @Column(name = "creator_user_id", nullable = false)
    private long creatorUserId;

    @Column(name = "approver_user_id")
    private long approverUserId;

    @Column(name = "rejection_reason")
    private String rejectionReason;
}