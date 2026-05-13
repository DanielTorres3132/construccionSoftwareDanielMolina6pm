package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.RegisterLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterLogRepository extends JpaRepository<RegisterLogEntity, Long> {

    List<RegisterLogEntity> findByUserId(long userId);

    List<RegisterLogEntity> findByAffectedProductId(String affectedProductId);

    List<RegisterLogEntity> findByOperationType(String operationType);
}