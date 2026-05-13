package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.NaturalPersonClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NaturalPersonClientRepository extends JpaRepository<NaturalPersonClientEntity, Long> {

    Optional<NaturalPersonClientEntity> findByIdentificationId(String identificationId);

    boolean existsByIdentificationId(String identificationId);
}