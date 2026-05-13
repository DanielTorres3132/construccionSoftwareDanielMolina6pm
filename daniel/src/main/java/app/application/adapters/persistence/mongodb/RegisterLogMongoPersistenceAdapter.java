package app.application.adapters.persistence.mongodb;

import app.application.adapters.persistence.mongodb.documents.RegisterLogDocument;
import app.application.adapters.persistence.mongodb.repositories.RegisterLogMongoRepository;
import app.domain.models.Log.RegisterLog;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.RegisterLogRepositoryPort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("mongoRegisterLog")
public class RegisterLogMongoPersistenceAdapter implements RegisterLogRepositoryPort {

    private final RegisterLogMongoRepository registerLogMongoRepository;

    public RegisterLogMongoPersistenceAdapter(RegisterLogMongoRepository registerLogMongoRepository) {
        this.registerLogMongoRepository = registerLogMongoRepository;
    }

    @Override
    public RegisterLog save(RegisterLog log) {
        RegisterLogDocument saved = registerLogMongoRepository.save(toDocument(log));
        return toModel(saved);
    }

    @Override
    public List<RegisterLog> findByUserId(long userId) {
        return registerLogMongoRepository.findByUserId(userId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<RegisterLog> findByAffectedProductId(String affectedProductId) {
        return registerLogMongoRepository.findByAffectedProductId(affectedProductId)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    @Override
    public List<RegisterLog> findByOperationType(String operationType) {
        return registerLogMongoRepository.findByOperationType(operationType)
                .stream().map(this::toModel).collect(Collectors.toList());
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private RegisterLogDocument toDocument(RegisterLog log) {
        RegisterLogDocument doc = new RegisterLogDocument();
        doc.setOperationType(log.getOperationType());
        doc.setOperationDateTime(log.getOperationDateTime());
        doc.setUserId(log.getUserId());
        doc.setUserRole(log.getUserRole() != null ? log.getUserRole().name() : null);
        doc.setAffectedProductId(log.getAffectedProductId());
        doc.setDetailData(log.getDetailData()); // MongoDB guarda Map directamente
        return doc;
    }

    private RegisterLog toModel(RegisterLogDocument doc) {
        if (doc == null) return null;
        RegisterLog log = new RegisterLog();
        log.setOperationType(doc.getOperationType());
        log.setOperationDateTime(doc.getOperationDateTime());
        log.setUserId(doc.getUserId());
        log.setUserRole(doc.getUserRole() != null ? SystemRole.valueOf(doc.getUserRole()) : null);
        log.setAffectedProductId(doc.getAffectedProductId());
        log.setDetailData(doc.getDetailData()); // MongoDB devuelve el Map directamente
        return log;
    }
}