package app.application.adapters.api.request;

import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenAccountRequest {
    @NotBlank(message = "El ID de identificación del cliente es obligatorio")
    private String clientIdentificationId;
    
    @NotNull(message = "El tipo de cuenta es obligatorio")
    private AccountType accountType;
    
    @NotNull(message = "La moneda es obligatoria")
    private Currency currency;
}
