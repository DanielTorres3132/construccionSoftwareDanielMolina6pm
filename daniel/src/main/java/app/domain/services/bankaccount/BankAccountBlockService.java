package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.ports.BankAccountRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class BankAccountBlockService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final BankAccountGetOrThrowService getOrThrowService;

    public BankAccountBlockService(BankAccountRepositoryPort bankAccountRepositoryPort,
                                   BankAccountGetOrThrowService getOrThrowService) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public BankAccount execute(String accountNumber) {
        BankAccount bankAccount = getOrThrowService.execute(accountNumber);
        if (bankAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("No se puede bloquear una cuenta cancelada.");
        }
        bankAccount.setAccountStatus(AccountStatus.BLOCKED);
        return bankAccountRepositoryPort.save(bankAccount);
    }
}
