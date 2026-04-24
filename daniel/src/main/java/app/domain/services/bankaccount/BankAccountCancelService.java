package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.ports.BankAccountRepositoryPort;

public class BankAccountCancelService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final BankAccountGetOrThrowService getOrThrowService;

    public BankAccountCancelService(BankAccountRepositoryPort bankAccountRepositoryPort,
                                    BankAccountGetOrThrowService getOrThrowService) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public BankAccount execute(String accountNumber) {
        BankAccount bankAccount = getOrThrowService.execute(accountNumber);
        if (bankAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("La cuenta ya está cancelada.");
        }
        bankAccount.setAccountStatus(AccountStatus.CANCELLED);
        return bankAccountRepositoryPort.save(bankAccount);
    }
}
