package app.domain.services.registerlog;

import app.domain.models.Log.RegisterLog;
import app.domain.ports.RegisterLogRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegisterLogFindByOperationTypeService {
    private final RegisterLogRepositoryPort registerLogRepositoryPort;

    public RegisterLogFindByOperationTypeService(RegisterLogRepositoryPort registerLogRepositoryPort) {
        this.registerLogRepositoryPort = registerLogRepositoryPort;
    }

    public List<RegisterLog> execute(String operationType) {
        if (operationType == null || operationType.isBlank())
            throw new BusinessException("Operation type is required");
        List<RegisterLog> logs = registerLogRepositoryPort.findByOperationType(operationType);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for operation type " + operationType);
        return logs;
    }
}
