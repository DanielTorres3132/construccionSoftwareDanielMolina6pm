package app.application.adapters.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOperativeUserRequest {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$", message = "El usuario debe tener entre 3-20 caracteres y solo puede contener letras, números y guiones bajos")
    private String username;
    
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe tener un formato válido")
    private String email;
    
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;
    
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;
    
    @NotBlank(message = "El ID de identificación es obligatorio")
    private String identificationId;
    
    @NotBlank(message = "El tipo de identificación es obligatorio")
    private String identificationType;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "El teléfono debe ser un formato internacional válido (E.164)")
    private String phone;
    
    @NotBlank(message = "La dirección es obligatoria")
    private String address;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", 
             message = "La contraseña debe tener al menos 8 caracteres e incluir letras, números y caracteres especiales (@$!%*#?&)")
    private String password;
}
