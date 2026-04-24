package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.ports.BankAccountRepositoryPort;
import java.util.Optional;

public class BankAccountFindByAccountNumberService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountFindByAccountNumberService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    public Optional<BankAccount> execute(String accountNumber) {
        return bankAccountRepositoryPort.findByAccountNumber(accountNumber);
    }
}
