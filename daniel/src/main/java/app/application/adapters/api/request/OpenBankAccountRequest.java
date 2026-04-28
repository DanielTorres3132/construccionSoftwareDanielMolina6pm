package app.application.adapters.api.request;

import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenBankAccountRequest {

    @NotNull(message = "El ID del titular es requerido")
    @Positive(message = "El ID del titular debe ser un número positivo")
    private Long holderId;

    @NotNull(message = "El tipo de cuenta es requerido")
    private AccountType accountType;

    @NotNull(message = "La moneda es requerida")
    private Currency currency;
}