package app.domain.services.naturalpersonclient;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class NaturalPersonClientFindByIdService {
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public NaturalPersonClientFindByIdService(NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    public NaturalPersonClient execute(long id) {
        return naturalPersonClientRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("Natural person client with ID " + id + " not found"));
    }
}
