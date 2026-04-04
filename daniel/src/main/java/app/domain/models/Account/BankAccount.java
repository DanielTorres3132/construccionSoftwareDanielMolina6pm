package app.domain.models.Account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;

@Setter
@Getter
@NoArgsConstructor
public class BankAccount { 
    private String accountNumber; 
    private AccountType accountType; 
    private String holderId; 
    private BigDecimal currentBalance; 
    private Currency currency; 
    private AccountStatus accountStatus; 
    private LocalDate openingDate; 
}