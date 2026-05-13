package app.application.adapters.persistence.sql.repositories;

import app.application.adapters.persistence.sql.entities.BankAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {

    Optional<BankAccountEntity> findByAccountNumber(String accountNumber);

    Optional<BankAccountEntity> findByHolderId(String holderId);
}