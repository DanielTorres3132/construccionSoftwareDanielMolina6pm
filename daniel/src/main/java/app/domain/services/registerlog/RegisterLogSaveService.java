package app.domain.services.registerlog;

import app.domain.models.Log.RegisterLog;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.RegisterLogRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RegisterLogSaveService {
    private final RegisterLogRepositoryPort registerLogRepositoryPort;

    public RegisterLogSaveService(RegisterLogRepositoryPort registerLogRepositoryPort) {
        this.registerLogRepositoryPort = registerLogRepositoryPort;
    }

    public RegisterLog execute(String operationType, long userId, SystemRole userRole, String affectedProductId, Map<String, Object> detailData) {
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
}
