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
public class BankAccount { //cuenta bancaria
    private String accountNumber; //numero de cuenta
    private AccountType accountType; //tipo de cuenta
    private String holderId; //identificacion
    private double currentBalance; //saldo actual 
    private String currency; //moneda
    private AccountStatus accountStatus; //estado de cuenta
    private LocalDate openingDate; //fecha de apertura
}