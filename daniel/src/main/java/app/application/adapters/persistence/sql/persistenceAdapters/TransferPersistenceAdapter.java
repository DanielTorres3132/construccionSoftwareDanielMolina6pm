package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.TransferEntity;
import app.application.adapters.persistence.sql.repositories.TransferRepository;
import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.ports.TransferRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransferPersistenceAdapter implements TransferRepositoryPort {

    private final TransferRepository transferRepository;

    @Override
    public Transfer save(Transfer transfer) {
        TransferEntity entity = toEntity(transfer);
        TransferEntity saved = transferRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Transfer> findById(long id) {
        return transferRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public List<Transfer> findBySourceAccount(String sourceAccount) {
        return transferRepository.findBySourceAccount(sourceAccount)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transfer> findByDestinationAccount(String destinationAccount) {
        return transferRepository.findByDestinationAccount(destinationAccount)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transfer> findByCreatorUserId(long creatorUserId) {
        return transferRepository.findByCreatorUserId(creatorUserId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transfer> findByTransferStatus(TransferStatus status) {
        return transferRepository.findByTransferStatus(status)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Transfer> findExpiredTransfers(LocalDateTime expirationThreshold) {
        return transferRepository.findExpiredTransfers(expirationThreshold)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    /**
     * Convierte una TransferEntity (capa SQL) al modelo de dominio Transfer.
     */
    private Transfer toDomain(TransferEntity entity) {
        Transfer transfer = new Transfer();
        transfer.setId(entity.getId());
        transfer.setSourceAccount(entity.getSourceAccount());
        transfer.setDestinationAccount(entity.getDestinationAccount());
        transfer.setAmount(entity.getAmount());
        transfer.setCreationDate(entity.getCreationDate());
        transfer.setApprovalDate(entity.getApprovalDate());
        transfer.setTransferStatus(entity.getTransferStatus());
        transfer.setCreatorUserId(entity.getCreatorUserId());
        transfer.setApproverUserId(entity.getApproverUserId());
        transfer.setRejectionReason(entity.getRejectionReason());
        return transfer;
    }

    /**
     * Convierte el modelo de dominio Transfer a una TransferEntity (capa SQL).
     */
    private TransferEntity toEntity(Transfer transfer) {
        return TransferEntity.builder()
                .id(transfer.getId() == 0 ? null : transfer.getId())
                .sourceAccount(transfer.getSourceAccount())
                .destinationAccount(transfer.getDestinationAccount())
                .amount(transfer.getAmount())
                .creationDate(transfer.getCreationDate())
                .approvalDate(transfer.getApprovalDate())
                .transferStatus(transfer.getTransferStatus())
                .creatorUserId(transfer.getCreatorUserId())
                .approverUserId(transfer.getApproverUserId())
                .rejectionReason(transfer.getRejectionReason())
                .build();
    }
}