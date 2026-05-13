package app.application.adapters.persistence.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.sql.entities.TransferEntity;
import app.domain.models.Transfer.enums.TransferStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> findBySourceAccount(String sourceAccount);

    List<TransferEntity> findByDestinationAccount(String destinationAccount);

    List<TransferEntity> findByCreatorUserId(long creatorUserId);

    List<TransferEntity> findByTransferStatus(TransferStatus transferStatus);

    @Query("SELECT t FROM TransferEntity t WHERE t.transferStatus = 'WAITING_FOR_APPROVAL' AND t.creationDate < :expirationThreshold")
    List<TransferEntity> findExpiredTransfers(@Param("expirationThreshold") LocalDateTime expirationThreshold);
}