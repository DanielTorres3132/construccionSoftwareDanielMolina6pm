package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlockOperativeUserRequest {
    @NotBlank(message = "La razón del bloqueo es obligatoria")
    private String blockReason;
}
