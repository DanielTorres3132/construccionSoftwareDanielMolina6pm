package app.domain.ports;

import app.domain.models.Client.NaturalPersonClient;
import java.util.Optional;

public interface NaturalPersonClientRepositoryPort {
    NaturalPersonClient save(NaturalPersonClient client);
    Optional<NaturalPersonClient> findById(long id);
    Optional<NaturalPersonClient> findByIdentificationId(String identificationId);
    boolean existsByIdentificationId(String identificationId);
}