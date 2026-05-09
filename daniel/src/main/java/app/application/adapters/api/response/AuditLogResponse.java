package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class AuditLogResponse {
    private long id;
    private String operationType;
    private LocalDateTime operationDateTime;
    private long userId;
    private String userRole;
    private String affectedProductId;
    private Map<String, Object> detailData;
}