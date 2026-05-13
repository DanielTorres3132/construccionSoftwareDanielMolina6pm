package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.NaturalPersonClientEntity;
import app.application.adapters.persistence.sql.repositories.NaturalPersonClientRepository;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NaturalPersonClientPersistenceAdapter implements NaturalPersonClientRepositoryPort {

    private final NaturalPersonClientRepository naturalPersonClientRepository;

    public NaturalPersonClientPersistenceAdapter(NaturalPersonClientRepository naturalPersonClientRepository) {
        this.naturalPersonClientRepository = naturalPersonClientRepository;
    }

    @Override
    public NaturalPersonClient save(NaturalPersonClient client) {
        NaturalPersonClientEntity saved = naturalPersonClientRepository.save(toEntity(client));
        return toModel(saved);
    }

    @Override
    public Optional<NaturalPersonClient> findById(long id) {
        return naturalPersonClientRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<NaturalPersonClient> findByIdentificationId(String identificationId) {
        return naturalPersonClientRepository.findByIdentificationId(identificationId).map(this::toModel);
    }

    @Override
    public boolean existsByIdentificationId(String identificationId) {
        return naturalPersonClientRepository.existsByIdentificationId(identificationId);
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private NaturalPersonClientEntity toEntity(NaturalPersonClient client) {
        NaturalPersonClientEntity e = new NaturalPersonClientEntity();
        e.setFullName(client.getFullName());
        e.setIdentificationId(client.getIdentificationId());
        e.setEmail(client.getEmail());
        e.setPhone(client.getPhone());
        e.setAddress(client.getAddress());
        e.setBirthDate(client.getBirthDate());
        return e;
    }

    private NaturalPersonClient toModel(NaturalPersonClientEntity e) {
        if (e == null) return null;
        NaturalPersonClient client = new NaturalPersonClient();
        client.setId(e.getId());
        client.setFullName(e.getFullName());
        client.setIdentificationId(e.getIdentificationId());
        client.setEmail(e.getEmail());
        client.setPhone(e.getPhone());
        client.setAddress(e.getAddress());
        client.setBirthDate(e.getBirthDate());
        return client;
    }
}