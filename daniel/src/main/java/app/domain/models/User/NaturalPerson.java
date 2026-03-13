package app.domain.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class NaturalPerson  extends User{
    private LocalDate birthDate;

}
