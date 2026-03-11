package app.domain.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor


public class CompanyClient extends User{
    private String companyName;
    private String nit;
    private String companyEmail;
    private String companyPhone;
    private String fiscalAddress;
    private User legalRepresentative;

}
