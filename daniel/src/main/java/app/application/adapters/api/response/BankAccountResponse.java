package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BankAccountResponse {
    private String accountNumber;
    private String accountType;
    private String holderId;
    private BigDecimal currentBalance;
    private String currency;
    private String accountStatus;
    private LocalDate openingDate;
}