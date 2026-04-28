package app.application.adapters.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateNaturalPersonClientRequest {

    @NotBlank(message = "El nombre completo es requerido")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String fullName;

    @NotBlank(message = "La identificación es requerida")
    @Size(min = 8, max = 20, message = "La identificación debe tener entre 8 y 20 caracteres")
    private String identificationId;

    @NotBlank(message = "El email es requerido")
    @Email(message = "El email debe ser válido")
    private String email;

    @NotBlank(message = "El teléfono es requerido")
    @Size(min = 7, max = 15, message = "El teléfono debe tener entre 7 y 15 dígitos")
    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "El teléfono contiene caracteres inválidos")
    private String phone;

    @NotBlank(message = "La dirección es requerida")
    @Size(min = 5, max = 255, message = "La dirección debe tener entre 5 y 255 caracteres")
    private String address;

    @NotNull(message = "La fecha de nacimiento es requerida")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}