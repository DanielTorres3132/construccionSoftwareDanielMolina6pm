package app.domain.services.registerlog;

import app.domain.models.Log.RegisterLog;
import app.domain.ports.RegisterLogRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RegisterLogFindByAffectedProductIdService {
    private final RegisterLogRepositoryPort registerLogRepositoryPort;

    public RegisterLogFindByAffectedProductIdService(RegisterLogRepositoryPort registerLogRepositoryPort) {
        this.registerLogRepositoryPort = registerLogRepositoryPort;
    }

    public List<RegisterLog> execute(String affectedProductId) {
        if (affectedProductId == null || affectedProductId.isBlank())
            throw new BusinessException("Affected product ID is required");
        List<RegisterLog> logs = registerLogRepositoryPort.findByAffectedProductId(affectedProductId);
        if (logs.isEmpty())
            throw new BusinessException("No logs found for product with ID " + affectedProductId);
        return logs;
    }
}
