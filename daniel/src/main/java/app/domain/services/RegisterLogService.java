package app.domain.services;

import app.domain.models.Log.RegisterLog;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.RegisterLogRepositoryPort;
import app.domain.Exceptions.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class RegisterLogService {

    private final RegisterLogRepositoryPort registerLogRepositoryPort;

    public RegisterLogService(RegisterLogRepositoryPort registerLogRepositoryPort) {
        this.registerLogRepositoryPort = registerLogRepositoryPort;
    }

    // ─── Guardar registro ─────────────────────────────────────────────────────

    public RegisterLog saveLog(String operationType,
                               long userId,
                               SystemRole userRole,
                               String affectedProductId,
                               Map<String, Object> detailData) {

        if (operationType == null || operationType.isBlank())
            throw new BusinessException("Operation type is required");

        if (userRole == null)
            throw new BusinessException("User role is required");

        if (affectedProductId == null || affectedProductId.isBlank())
            throw new BusinessException("Affected product ID is required");

        RegisterLog log = new RegisterLog();
        log.setOperationType(operationType);
        log.setOperationDateTime(LocalDateTime.now());
        log.setUserId(userId);
        log.setUserRole(userRole);
        log.setAffectedProductId(affectedProductId);
        log.setDetailData(detailData);

        return registerLogRepositoryPort.save(log);
    }

    // ─── Buscar por usuario ───────────────────────────────────────────────────

    public List<RegisterLog> findByUserId(long userId) {
        List<RegisterLog> logs = registerLogRepositoryPort.findByUserId(userId);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for user with ID " + userId);
        return logs;
    }

    // ─── Buscar por producto ──────────────────────────────────────────────────

    public List<RegisterLog> findByAffectedProductId(String affectedProductId) {
        if (affectedProductId == null || affectedProductId.isBlank())
            throw new BusinessException("Affected product ID is required");

        List<RegisterLog> logs = registerLogRepositoryPort.findByAffectedProductId(affectedProductId);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for product with ID " + affectedProductId);
        return logs;
    }

    // ─── Buscar por tipo de operación ─────────────────────────────────────────

    public List<RegisterLog> findByOperationType(String operationType) {
        if (operationType == null || operationType.isBlank())
            throw new BusinessException("Operation type is required");

        List<RegisterLog> logs = registerLogRepositoryPort.findByOperationType(operationType);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for operation type " + operationType);
        return logs;
    }
}