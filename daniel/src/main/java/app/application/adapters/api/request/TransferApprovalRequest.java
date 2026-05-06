package app.application.adapters.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferApprovalRequest {
    @NotBlank(message = "Las notas de aprobación son obligatorias")
    private String approvalNotes;
}
