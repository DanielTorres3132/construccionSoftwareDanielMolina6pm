package app.application.adapters.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTransferRequest {

    @NotBlank(message = "El número de cuenta origen es requerido")
    private String sourceAccount;

    @NotBlank(message = "El número de cuenta destino es requerido")
    private String destinationAccount;

    @NotNull(message = "El monto es requerido")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
    private BigDecimal amount;
}