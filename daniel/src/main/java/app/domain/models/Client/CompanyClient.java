package app.domain.models.Client;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CompanyClient {
    private long id;
    private String companyName;
    private String nit;
    private String companyEmail;
    private String companyPhone;
    private String fiscalAddress;
    private String legalRepresentativeId;
}