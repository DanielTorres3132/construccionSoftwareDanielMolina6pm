package app.domain.services.companyclient;

import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class CompanyClientFindByIdService {
    private final CompanyClientRepositoryPort companyClientRepositoryPort;

    public CompanyClientFindByIdService(CompanyClientRepositoryPort companyClientRepositoryPort) {
        this.companyClientRepositoryPort = companyClientRepositoryPort;
    }

    public CompanyClient execute(long id) {
        return companyClientRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("Company client with ID " + id + " not found"));
    }
}
