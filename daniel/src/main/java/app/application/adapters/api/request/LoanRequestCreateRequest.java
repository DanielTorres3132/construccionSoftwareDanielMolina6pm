package app.application.adapters.api.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LoanRequestCreateRequest {
    @NotBlank(message = "El tipo de préstamo es obligatorio")
    private String loanType;
    
    @NotNull(message = "El monto solicitado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0.01")
    private BigDecimal requestedAmount;
    
    @NotNull(message = "El plazo en meses es obligatorio")
    @Min(value = 1, message = "El plazo debe ser mínimo 1 mes")
    private int termMonths;
}
