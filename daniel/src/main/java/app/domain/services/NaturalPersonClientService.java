package app.domain.services;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.ports.NaturalPersonClientRepositoryPort;
import app.domain.Exceptions.BusinessException;

import java.time.LocalDate;
import java.time.Period;

public class NaturalPersonClientService {

    private final NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort;

    public NaturalPersonClientService(NaturalPersonClientRepositoryPort naturalPersonClientRepositoryPort) {
        this.naturalPersonClientRepositoryPort = naturalPersonClientRepositoryPort;
    }

    // ─── Crear cliente ────────────────────────────────────────────────────────

    public NaturalPersonClient createClient(NaturalPersonClient client) {
        validateRequiredFields(client);

        if (naturalPersonClientRepositoryPort.existsByIdentificationId(client.getIdentificationId()))
            throw new BusinessException("A client with identification ID " + client.getIdentificationId() + " already exists");

        if (!isAdult(client.getBirthDate()))
            throw new BusinessException("Client must be at least 18 years old");

        return naturalPersonClientRepositoryPort.save(client);
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public NaturalPersonClient findById(long id) {
        return naturalPersonClientRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("Natural person client with ID " + id + " not found"));
    }

    public NaturalPersonClient findByIdentificationId(String identificationId) {
        return naturalPersonClientRepositoryPort.findByIdentificationId(identificationId)
                .orElseThrow(() -> new BusinessException("Natural person client with identification ID " + identificationId + " not found"));
    }

    // ─── Validaciones internas ────────────────────────────────────────────────

    private boolean isAdult(LocalDate birthDate) {
        if (birthDate == null) return false;
        return Period.between(birthDate, LocalDate.now()).getYears() >= 18;
    }

    private void validateRequiredFields(NaturalPersonClient client) {
        if (client.getFullName() == null || client.getFullName().isBlank())
            throw new BusinessException("Full name is required");

        if (client.getIdentificationId() == null || client.getIdentificationId().isBlank())
            throw new BusinessException("Identification ID is required");

        if (client.getEmail() == null || !client.getEmail().contains("@") || !client.getEmail().contains("."))
            throw new BusinessException("Email is invalid");

        if (client.getPhone() == null || client.getPhone().length() < 7 || client.getPhone().length() > 15)
            throw new BusinessException("Phone must be between 7 and 15 digits");

        if (client.getAddress() == null || client.getAddress().isBlank())
            throw new BusinessException("Address is required");

        if (client.getBirthDate() == null)
            throw new BusinessException("Birth date is required");
    }
}
