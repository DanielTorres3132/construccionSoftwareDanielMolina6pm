package app.domain.services.companyclient;

import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class CompanyClientCreateService {
    private final CompanyClientRepositoryPort companyClientRepositoryPort;
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public CompanyClientCreateService(CompanyClientRepositoryPort companyClientRepositoryPort,
                                      NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.companyClientRepositoryPort = companyClientRepositoryPort;
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    public CompanyClient execute(CompanyClient client) {
        if (companyClientRepositoryPort.existsByNit(client.getNit()))
            throw new BusinessException("A company with NIT " + client.getNit() + " already exists");
        if (!naturalPersonClientRepositoryPort.existsByIdentificationId(client.getLegalRepresentativeId()))
            throw new BusinessException("Legal representative with identification ID " + client.getLegalRepresentativeId() + " not found");
        return companyClientRepositoryPort.save(client);
    }
}
