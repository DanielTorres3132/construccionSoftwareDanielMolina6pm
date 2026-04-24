package app.domain.services.registerlog;

import app.domain.models.Log.RegisterLog;
import app.domain.ports.RegisterLogRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.util.List;

public class RegisterLogFindByUserIdService {
    private final RegisterLogRepositoryPort registerLogRepositoryPort;

    public RegisterLogFindByUserIdService(RegisterLogRepositoryPort registerLogRepositoryPort) {
        this.registerLogRepositoryPort = registerLogRepositoryPort;
    }

    public List<RegisterLog> execute(long userId) {
        List<RegisterLog> logs = registerLogRepositoryPort.findByUserId(userId);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for user with ID " + userId);
        return logs;
    }
}
