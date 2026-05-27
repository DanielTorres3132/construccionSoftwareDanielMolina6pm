package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.ports.BankAccountRepositoryPort;
import app.domain.Exceptions.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BankAccountGetOrThrowService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountGetOrThrowService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    public BankAccount execute(String accountNumber) {
        return bankAccountRepositoryPort.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new NotFoundException(
                "No se encontró la cuenta con número: " + accountNumber));
    }
}
