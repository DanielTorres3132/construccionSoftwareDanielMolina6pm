package app.application.adapters.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanApprovalRequest {
    @NotNull(message = "El monto aprobado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.01")
    private BigDecimal approvedAmount;
    
    @NotNull(message = "La tasa de interés es obligatoria")
    @DecimalMin(value = "0.01", message = "La tasa de interés debe ser mayor a 0.01")
    private BigDecimal interestRate;
}
