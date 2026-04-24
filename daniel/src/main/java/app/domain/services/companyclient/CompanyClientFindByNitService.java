package app.domain.services.companyclient;

import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class CompanyClientFindByNitService {
    private final CompanyClientRepositoryPort companyClientRepositoryPort;

    public CompanyClientFindByNitService(CompanyClientRepositoryPort companyClientRepositoryPort) {
        this.companyClientRepositoryPort = companyClientRepositoryPort;
    }

    public CompanyClient execute(String nit) {
        return companyClientRepositoryPort.findByNit(nit)
                .orElseThrow(() -> new BusinessException("Company client with NIT " + nit + " not found"));
    }
}
