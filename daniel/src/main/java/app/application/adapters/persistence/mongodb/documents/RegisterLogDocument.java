package app.application.adapters.persistence.mongodb.documents;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Document(collection = "register_logs")
public class RegisterLogDocument {

    @Id
    private String id;

    private String operationType;

    private LocalDateTime operationDateTime;

    private long userId;

    private String userRole; // SystemRole guardado como String

    private String affectedProductId;

    /**
     * Datos adicionales del log en formato flexible.
     * MongoDB guarda Map<String, Object> nativamente como subdocumento BSON.
     */
    private Map<String, Object> detailData;
}