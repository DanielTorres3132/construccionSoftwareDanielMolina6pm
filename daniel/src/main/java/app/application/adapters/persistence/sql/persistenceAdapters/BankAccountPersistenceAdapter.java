package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.BankAccountEntity;
import app.application.adapters.persistence.sql.repositories.BankAccountRepository;
import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import app.domain.ports.BankAccountRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankAccountPersistenceAdapter implements BankAccountRepositoryPort {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountPersistenceAdapter(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public BankAccount save(BankAccount bankAccount) {
        BankAccountEntity saved = bankAccountRepository.save(toEntity(bankAccount));
        return toModel(saved);
    }

    @Override
    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber).map(this::toModel);
    }

    @Override
    public Optional<BankAccount> findByHolderId(String holderId) {
        return bankAccountRepository.findByHolderId(holderId).map(this::toModel);
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private BankAccountEntity toEntity(BankAccount bankAccount) {
        BankAccountEntity e = new BankAccountEntity();
        e.setAccountNumber(bankAccount.getAccountNumber());
        e.setAccountType(bankAccount.getAccountType() != null ? bankAccount.getAccountType().name() : null);
        e.setHolderId(bankAccount.getHolderId());
        e.setCurrentBalance(bankAccount.getCurrentBalance());
        e.setCurrency(bankAccount.getCurrency() != null ? bankAccount.getCurrency().name() : null);
        e.setAccountStatus(bankAccount.getAccountStatus() != null ? bankAccount.getAccountStatus().name() : null);
        e.setOpeningDate(bankAccount.getOpeningDate());
        return e;
    }

    private BankAccount toModel(BankAccountEntity e) {
        if (e == null) return null;
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(e.getAccountNumber());
        bankAccount.setAccountType(e.getAccountType() != null ? AccountType.valueOf(e.getAccountType()) : null);
        bankAccount.setHolderId(e.getHolderId());
        bankAccount.setCurrentBalance(e.getCurrentBalance());
        bankAccount.setCurrency(e.getCurrency() != null ? Currency.valueOf(e.getCurrency()) : null);
        bankAccount.setAccountStatus(e.getAccountStatus() != null ? AccountStatus.valueOf(e.getAccountStatus()) : null);
        bankAccount.setOpeningDate(e.getOpeningDate());
        return bankAccount;
    }
}