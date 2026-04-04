package app.domain.ports;

import app.domain.models.Account.BankAccount;
import java.util.Optional;

public interface BankAccountRepositoryPort {
    BankAccount save(BankAccount bankAccount);
    Optional<BankAccount> findByAccountNumber(String accountNumber);
    Optional<BankAccount> findByHolderId(String holderId);

}
