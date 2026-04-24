package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.ports.BankAccountRepositoryPort;
import java.util.Optional;

public class BankAccountFindByHolderIdService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountFindByHolderIdService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    public Optional<BankAccount> execute(String holderId) {
        return bankAccountRepositoryPort.findByHolderId(holderId);
    }
}
