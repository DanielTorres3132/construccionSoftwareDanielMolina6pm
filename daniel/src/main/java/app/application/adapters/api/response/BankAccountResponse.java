package app.application.adapters.api.response;

import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountStatus;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de respuesta para cuentas bancarias.
 * 
 * Transporta la información de una cuenta bancaria hacia la respuesta HTTP.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankAccountResponse {

    private String accountNumber;

    private String holderId;

    private AccountType accountType;

    private Currency currency;

    private BigDecimal currentBalance;

    private AccountStatus accountStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate openingDate;

    /**
     * Convierte un modelo de dominio BankAccount a respuesta.
     * 
     * @param account Cuenta bancaria del dominio
     * @return DTO de respuesta
     */
    public static BankAccountResponse fromBankAccount(BankAccount account) {
        return BankAccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .holderId(account.getHolderId())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .currentBalance(account.getCurrentBalance())
                .accountStatus(account.getAccountStatus())
                .openingDate(account.getOpeningDate())
                .build();
    }
}
