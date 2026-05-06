package app.application.adapters.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferCreateRequest {
    @NotBlank(message = "La cuenta de origen es obligatoria")
    private String sourceAccount;
    
    @NotBlank(message = "La cuenta de destino es obligatoria")
    private String destinationAccount;
    
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.01")
    private BigDecimal amount;
    
    private String description;
}
