package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.ports.BankAccountRepositoryPort;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class BankAccountGetBalanceService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
    private final BankAccountGetOrThrowService getOrThrowService;

    public BankAccountGetBalanceService(BankAccountRepositoryPort bankAccountRepositoryPort,
                                        BankAccountGetOrThrowService getOrThrowService) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
        this.getOrThrowService = getOrThrowService;
    }

    public BigDecimal execute(String accountNumber) {
        BankAccount bankAccount = getOrThrowService.execute(accountNumber);
        if (bankAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Solo se puede consultar el saldo de cuentas activas.");
        }
        return bankAccount.getCurrentBalance();
    }
}
