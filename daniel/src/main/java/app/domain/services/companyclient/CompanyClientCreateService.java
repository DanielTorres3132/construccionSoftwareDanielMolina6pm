package app.domain.services.companyclient;

import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class CompanyClientCreateService {
    private final CompanyClientRepositoryPort companyClientRepositoryPort;
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public CompanyClientCreateService(CompanyClientRepositoryPort companyClientRepositoryPort,
                                      NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.companyClientRepositoryPort = companyClientRepositoryPort;
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    public CompanyClient execute(CompanyClient client) {
        validateRequiredFields(client);
        if (companyClientRepositoryPort.existsByNit(client.getNit()))
            throw new BusinessException("A company with NIT " + client.getNit() + " already exists");
        if (!naturalPersonClientRepositoryPort.existsByIdentificationId(client.getLegalRepresentativeId()))
            throw new BusinessException("Legal representative with identification ID " + client.getLegalRepresentativeId() + " not found");
        return companyClientRepositoryPort.save(client);
    }

    private void validateRequiredFields(CompanyClient client) {
        if (client.getCompanyName() == null || client.getCompanyName().isBlank())
            throw new BusinessException("Company name is required");
        if (client.getNit() == null || client.getNit().isBlank())
            throw new BusinessException("NIT is required");
        if (client.getCompanyEmail() == null || !client.getCompanyEmail().contains("@") || !client.getCompanyEmail().contains("."))
            throw new BusinessException("Company email is invalid");
        if (client.getCompanyPhone() == null || client.getCompanyPhone().length() < 7 || client.getCompanyPhone().length() > 15)
            throw new BusinessException("Company phone must be between 7 and 15 digits");
        if (client.getFiscalAddress() == null || client.getFiscalAddress().isBlank())
            throw new BusinessException("Fiscal address is required");
        if (client.getLegalRepresentativeId() == null || client.getLegalRepresentativeId().isBlank())
            throw new BusinessException("Legal representative is required");
    }
}
