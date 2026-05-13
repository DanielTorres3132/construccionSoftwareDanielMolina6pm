package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    Optional<LoanEntity> findByApplicantClientId(String applicantClientId);
}