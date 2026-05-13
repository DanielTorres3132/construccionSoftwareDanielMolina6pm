package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.RegisterLogEntity;
import app.application.adapters.persistence.sql.repositories.RegisterLogRepository;
import app.domain.models.Log.RegisterLog;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.RegisterLogRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegisterLogPersistenceAdapter implements RegisterLogRepositoryPort {

    private final RegisterLogRepository registerLogRepository;

    public RegisterLogPersistenceAdapter(RegisterLogRepository registerLogRepository) {
        this.registerLogRepository = registerLogRepository;
    }

    @Override
    public RegisterLog save(RegisterLog log) {
        RegisterLogEntity saved = registerLogRepository.save(toEntity(log));
        return toModel(saved);
    }

    @Override
    public List<RegisterLog> findByUserId(long userId) {
        return registerLogRepository.findByUserId(userId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<RegisterLog> findByAffectedProductId(String affectedProductId) {
        return registerLogRepository.findByAffectedProductId(affectedProductId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<RegisterLog> findByOperationType(String operationType) {
        return registerLogRepository.findByOperationType(operationType)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private RegisterLogEntity toEntity(RegisterLog log) {
    RegisterLogEntity e = new RegisterLogEntity();
    e.setOperationType(log.getOperationType());
    e.setOperationDateTime(log.getOperationDateTime());
    e.setUserId(log.getUserId());
    e.setUserRole(log.getUserRole() != null ? log.getUserRole().name() : null);
    e.setAffectedProductId(log.getAffectedProductId());
    e.setDetailData(log.getDetailData() != null ? log.getDetailData().toString() : null);
    return e;
}

private RegisterLog toModel(RegisterLogEntity e) {
    if (e == null) return null;
    RegisterLog log = new RegisterLog();
    log.setId(e.getId());
    log.setOperationType(e.getOperationType());
    log.setOperationDateTime(e.getOperationDateTime());
    log.setUserId(e.getUserId());
    log.setUserRole(e.getUserRole() != null ? SystemRole.valueOf(e.getUserRole()) : null);
    log.setAffectedProductId(e.getAffectedProductId());
    log.setDetailData(null); // sin Jackson no se puede deserializar
    return log;
}
}