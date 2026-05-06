package app.application.adapters.api.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyClientCreateRequest {
    @NotBlank
    private String nit;
    @NotBlank
    private String companyName;
    @NotBlank
    private String address;
    @NotBlank
    private String phone;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String legalRepresentativeId;
}
