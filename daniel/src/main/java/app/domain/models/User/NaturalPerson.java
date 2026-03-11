package app.domain.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import app.domain.models.User.enums.SystemRole;

@Getter
@Setter
@NoArgsConstructor

public class NaturalPerson  extends User{
    private LocalDate birthDate;
    private SystemRole systemRole = SystemRole.NATURAL_PERSON_CLIENT;

}
