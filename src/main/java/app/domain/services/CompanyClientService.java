package app.domain.services;

import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class CompanyClientService {

    private final CompanyClientRepositoryPort companyClientRepositoryPort;
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public CompanyClientService(CompanyClientRepositoryPort companyClientRepositoryPort,
                                NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.companyClientRepositoryPort = companyClientRepositoryPort;
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    // ─── Crear cliente empresa ────────────────────────────────────────────────

    public CompanyClient createClient(CompanyClient client) {
        validateRequiredFields(client);

        if (companyClientRepositoryPort.existsByNit(client.getNit()))
            throw new BusinessException("A company with NIT " + client.getNit() + " already exists");

        // El representante legal debe existir como persona natural en el sistema
        if (!naturalPersonClientRepositoryPort.existsByIdentificationId(client.getLegalRepresentativeId()))
            throw new BusinessException("Legal representative with identification ID " + client.getLegalRepresentativeId() + " not found");

        return companyClientRepositoryPort.save(client);
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public CompanyClient findById(long id) {
        return companyClientRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("Company client with ID " + id + " not found"));
    }

    public CompanyClient findByNit(String nit) {
        return companyClientRepositoryPort.findByNit(nit)
                .orElseThrow(() -> new BusinessException("Company client with NIT " + nit + " not found"));
    }

    // ─── Validaciones internas ────────────────────────────────────────────────

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
