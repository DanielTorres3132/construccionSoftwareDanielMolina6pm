package app.application.adapters.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaturalPersonClientCreateRequest {
    @NotBlank
    private String identificationId;
    @NotBlank
    private String fullName;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotNull
    private LocalDate birthDate;
}
