package app.dominio.models.Transferencia;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import app.dominio.models.Transferencia.enums.EstadoTransferencia;

@Setter
@Getter
@NoArgsConstructor
public class Transferencia {
    private long id;
    private String cuentaOrigen;
    private String cuentaDestino;
    private double monto;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaAprobacion;
    private EstadoTransferencia estadoTransferencia;
    private long idUsuarioCreador;
    private long idUsuarioAprobador;
    private String motivoRechazo;
}