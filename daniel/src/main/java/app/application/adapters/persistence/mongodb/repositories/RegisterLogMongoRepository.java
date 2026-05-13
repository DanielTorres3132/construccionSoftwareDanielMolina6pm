package app.application.adapters.persistence.mongodb.repositories;

import app.application.adapters.persistence.mongodb.documents.RegisterLogDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegisterLogMongoRepository extends MongoRepository<RegisterLogDocument, String> {

    List<RegisterLogDocument> findByUserId(long userId);

    List<RegisterLogDocument> findByAffectedProductId(String affectedProductId);

    List<RegisterLogDocument> findByOperationType(String operationType);
}