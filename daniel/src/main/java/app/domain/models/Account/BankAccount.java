package app.domain.models.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;

@Setter
@Getter
@NoArgsConstructor
public class BankAccount {
    private String accountNumber;
    private AccountType accountType;
    private String holderId;
    private double currentBalance;
    private String currency;
    private AccountStatus accountStatus;
    private LocalDate openingDate;
}