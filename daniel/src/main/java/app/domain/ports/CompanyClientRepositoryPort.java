package app.domain.ports;

import app.domain.models.Client.CompanyClient;

import java.util.Optional;

public interface CompanyClientRepositoryPort {
    CompanyClient save(CompanyClient client);
    Optional<CompanyClient> findById(long id);
    Optional<CompanyClient> findByNit(String nit);
    boolean existsByNit(String nit);
}
