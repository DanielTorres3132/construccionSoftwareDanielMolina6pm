package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class NaturalPersonClientResponse {
    private Long id;
    private String identificationId;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private LocalDate birthDate;
}
