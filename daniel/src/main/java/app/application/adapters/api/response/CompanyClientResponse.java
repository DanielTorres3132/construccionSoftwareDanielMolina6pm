package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyClientResponse {
    private Long id;
    private String nit;
    private String companyName;
    private String address;
    private String phone;
    private String email;
    private String legalRepresentativeId;
}
