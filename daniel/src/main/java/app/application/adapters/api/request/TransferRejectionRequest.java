package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRejectionRequest {
    @NotBlank(message = "La razón del rechazo es obligatoria")
    private String rejectionReason;
}
