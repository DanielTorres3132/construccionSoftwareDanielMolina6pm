package app.domain.services.naturalpersonclient;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Service;

@Service
public class NaturalPersonClientCreateService {
    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public NaturalPersonClientCreateService(NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    public NaturalPersonClient execute(NaturalPersonClient client) {
        if (naturalPersonClientRepositoryPort.existsByIdentificationId(client.getIdentificationId()))
            throw new BusinessException("A client with identification ID " + client.getIdentificationId() + " already exists");
        if (!isAdult(client.getBirthDate()))
            throw new BusinessException("Client must be at least 18 years old");
        return naturalPersonClientRepositoryPort.save(client);
    }

    private boolean isAdult(LocalDate birthDate) {
        if (birthDate == null) return false;
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }
}
