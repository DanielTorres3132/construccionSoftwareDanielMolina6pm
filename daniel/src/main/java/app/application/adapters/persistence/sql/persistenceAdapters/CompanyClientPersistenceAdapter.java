package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.CompanyClientEntity;
import app.application.adapters.persistence.sql.repositories.CompanyClientRepository;
import app.domain.models.Client.CompanyClient;
import app.domain.ports.CompanyClientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyClientPersistenceAdapter implements CompanyClientRepositoryPort {

    private final CompanyClientRepository companyClientRepository;

    public CompanyClientPersistenceAdapter(CompanyClientRepository companyClientRepository) {
        this.companyClientRepository = companyClientRepository;
    }

    @Override
    public CompanyClient save(CompanyClient client) {
        CompanyClientEntity saved = companyClientRepository.save(toEntity(client));
        return toModel(saved);
    }

    @Override
    public Optional<CompanyClient> findById(long id) {
        return companyClientRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<CompanyClient> findByNit(String nit) {
        return companyClientRepository.findByNit(nit).map(this::toModel);
    }

    @Override
    public boolean existsByNit(String nit) {
        return companyClientRepository.existsByNit(nit);
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private CompanyClientEntity toEntity(CompanyClient client) {
        CompanyClientEntity e = new CompanyClientEntity();
        e.setCompanyName(client.getCompanyName());
        e.setNit(client.getNit());
        e.setCompanyEmail(client.getCompanyEmail());
        e.setCompanyPhone(client.getCompanyPhone());
        e.setFiscalAddress(client.getFiscalAddress());
        e.setLegalRepresentativeId(client.getLegalRepresentativeId());
        return e;
    }

    private CompanyClient toModel(CompanyClientEntity e) {
        if (e == null) return null;
        CompanyClient client = new CompanyClient();
        client.setId(e.getId());
        client.setCompanyName(e.getCompanyName());
        client.setNit(e.getNit());
        client.setCompanyEmail(e.getCompanyEmail());
        client.setCompanyPhone(e.getCompanyPhone());
        client.setFiscalAddress(e.getFiscalAddress());
        client.setLegalRepresentativeId(e.getLegalRepresentativeId());
        return client;
    }
}