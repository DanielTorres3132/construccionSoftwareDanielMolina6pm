package app.dominio.models.Bitacora;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import app.dominio.models.Usuario.enums.RolSistema;

@Setter
@Getter
@NoArgsConstructor
public class RegistroBitacora {
    private long id;
    private String tipoOperacion;
    private LocalDateTime fechaHoraOperacion;
    private long idUsuario;
    private RolSistema rolUsuario;
    private String idProductoAfectado;
    private String datosDetalle;
}