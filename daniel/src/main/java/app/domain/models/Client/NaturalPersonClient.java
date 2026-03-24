package app.domain.models.Client;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class NaturalPersonClient {
    private long id;
    private String fullName;
    private String identificationId;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthDate;
}