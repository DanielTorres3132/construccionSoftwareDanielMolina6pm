package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanDisbursementRequest {
    @NotBlank(message = "La cuenta de desembolso es obligatoria")
    private String disbursementAccount;
}
