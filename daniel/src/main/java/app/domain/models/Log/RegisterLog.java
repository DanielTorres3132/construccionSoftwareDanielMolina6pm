package app.domain.models.Log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import app.domain.models.User.enums.SystemRole;

@Setter
@Getter
@NoArgsConstructor
public class RegisterLog {
    private long id;
    private String operationType;
    private LocalDateTime operationDateTime;
    private long userId;
    private SystemRole userRole;
    private String affectedProductId;
    private String detailData;
}