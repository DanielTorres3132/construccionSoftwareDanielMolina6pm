package app.application.adapters.persistence.sql.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.application.adapters.persistence.sql.entities.TransferEntity;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    List<TransferEntity> findBySourceAccount(String sourceAccount);

    List<TransferEntity> findByDestinationAccount(String destinationAccount);
}