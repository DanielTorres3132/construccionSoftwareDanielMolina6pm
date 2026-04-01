package app.domain.ports;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransferRepositoryPort {
    Transfer save(Transfer transfer);
    Optional<Transfer> findById(long id);
    List<Transfer> findBySourceAccount(String sourceAccount);
    List<Transfer> findByDestinationAccount(String destinationAccount);
    List<Transfer> findByCreatorUserId(long creatorUserId);
    List<Transfer> findByTransferStatus(TransferStatus status);
    List<Transfer> findExpiredTransfers(LocalDateTime expirationThreshold);
}