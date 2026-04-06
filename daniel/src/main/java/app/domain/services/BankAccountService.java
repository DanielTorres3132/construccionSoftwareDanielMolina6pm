package app.domain.services;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import app.domain.ports.BankAccountRepositoryPort;
import lombok.RequiredArgsConstructor;
 
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepositoryPort bankAccountRepositoryPort;
 
    // Abrir cuenta
    public BankAccount openAccount(String holderId, AccountType accountType, Currency currency) {
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

     // Buscar por número de cuenta
    public Optional<BankAccount> findByAccountNumber(String accountNumber) {
        return bankAccountRepositoryPort.findByAccountNumber(accountNumber);
    }
 
    // Buscar por titular (holderId)
    public Optional<BankAccount> findByHolderId(String holderId) {
        return bankAccountRepositoryPort.findByHolderId(holderId);
    }
 
    // Bloquear cuenta
    public BankAccount blockAccount(String accountNumber) {
        BankAccount bankAccount = getAccountOrThrow(accountNumber);
 
        if (bankAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("No se puede bloquear una cuenta cancelada.");
        }
 
        bankAccount.setAccountStatus(AccountStatus.BLOCKED);
        return bankAccountRepositoryPort.save(bankAccount);
    }

    // Cancelar cuenta
    public BankAccount cancelAccount(String accountNumber) {
        BankAccount bankAccount = getAccountOrThrow(accountNumber);
 
        if (bankAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new IllegalStateException("La cuenta ya está cancelada.");
        }
 
        bankAccount.setAccountStatus(AccountStatus.CANCELLED);
        return bankAccountRepositoryPort.save(bankAccount);
    }
 
    // Consultar saldo
    public BigDecimal getBalance(String accountNumber) {
        BankAccount bankAccount = getAccountOrThrow(accountNumber);
 
        if (bankAccount.getAccountStatus() != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Solo se puede consultar el saldo de cuentas activas.");
        }
 
        return bankAccount.getCurrentBalance();
    }

    private BankAccount getAccountOrThrow(String accountNumber) {
        return bankAccountRepositoryPort.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la cuenta con número: " + accountNumber));
    }
 
    private String generateAccountNumber() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }

}
