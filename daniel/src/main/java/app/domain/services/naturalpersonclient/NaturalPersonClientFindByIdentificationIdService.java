package app.domain.services.naturalpersonclient;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class NaturalPersonClientFindByIdentificationIdService {
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public NaturalPersonClientFindByIdentificationIdService(NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    public NaturalPersonClient execute(String identificationId) {
        return naturalPersonClientRepositoryPort.findByIdentificationId(identificationId)
                .orElseThrow(() -> new BusinessException("Natural person client with identification ID " + identificationId + " not found"));
    }
}
