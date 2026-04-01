package app.domain.models.Log;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.Map;

import app.domain.models.User.enums.SystemRole;

@Setter
@Getter
@NoArgsConstructor
public class RegisterLog { // registro de bitacora
    private long id;
    private String operationType; //tipo de operacion
    private LocalDateTime operationDateTime; //fecha y hora de la operacion
    private long userId; //id del usuario
    private SystemRole userRole; //rol del usuario
    private String affectedProductId; //id del producto afectado
    private Map<String, Object> detailData; //detalle de los datos
}