package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AuditLogResponse {
    private Long id;
    private Long userId;
    private String operationType;
    private String description;
    private Long productId;
    private String productType;
    private LocalDateTime timestamp;
    private String details;
    private String status;
}
