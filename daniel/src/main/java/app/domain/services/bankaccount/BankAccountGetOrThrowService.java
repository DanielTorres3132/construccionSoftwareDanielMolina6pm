package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.ports.BankAccountRepositoryPort;

public class BankAccountGetOrThrowService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountGetOrThrowService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    public BankAccount execute(String accountNumber) {
        return bankAccountRepositoryPort.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la cuenta con número: " + accountNumber));
    }
}
