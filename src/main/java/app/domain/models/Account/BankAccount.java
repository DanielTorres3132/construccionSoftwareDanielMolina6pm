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
public class BankAccount { //cuenta bancaria
    private String accountNumber; //numero de cuenta
    private AccountType accountType; //tipo de cuenta
    private String holderId; //identificacion
    private BigDecimal currentBalance; //saldo actual 
    private Currency currency; //moneda
    private AccountStatus accountStatus; //estado de cuenta
    private LocalDate openingDate; //fecha de apertura
}