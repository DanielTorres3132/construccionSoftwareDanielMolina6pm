package app.domain.services.bankaccount;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import app.domain.ports.BankAccountRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BankAccountOpenService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;

    public BankAccountOpenService(BankAccountRepositoryPort bankAccountRepositoryPort) {
        this.bankAccountRepositoryPort = bankAccountRepositoryPort;
    }

    public BankAccount execute(String holderId, AccountType accountType, Currency currency) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setHolderId(holderId);
        bankAccount.setAccountType(accountType);
        bankAccount.setCurrency(currency);
        bankAccount.setCurrentBalance(BigDecimal.ZERO);
        bankAccount.setAccountStatus(AccountStatus.ACTIVE);
        bankAccount.setOpeningDate(LocalDate.now());
        return bankAccountRepositoryPort.save(bankAccount);
    }

    private String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}
