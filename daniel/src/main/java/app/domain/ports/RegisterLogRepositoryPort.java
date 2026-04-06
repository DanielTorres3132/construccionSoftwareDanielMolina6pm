package app.domain.ports;

import app.domain.models.Log.RegisterLog;

import java.util.List;

public interface RegisterLogRepositoryPort {
    RegisterLog save(RegisterLog log);
    List<RegisterLog> findByUserId(long userId);
    List<RegisterLog> findByAffectedProductId(String affectedProductId);
    List<RegisterLog> findByOperationType(String operationType);
}