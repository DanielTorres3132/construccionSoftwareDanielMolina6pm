package app.dominio.models.Prestamo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import app.dominio.models.Prestamo.enums.EstadoPrestamo;
import app.dominio.models.Prestamo.enums.TipoPrestamo;

@Setter
@Getter
@NoArgsConstructor
public class Prestamo {
    private long id;
    private TipoPrestamo tipoPrestamo;
    private String idClienteSolicitante;
    private double montoSolicitado;
    private double montoAprobado;
    private double tasaInteres;
    private int plazoMeses;
    private EstadoPrestamo estadoPrestamo;
    private LocalDate fechaSolicitud;
    private LocalDate fechaAprobacion;
    private LocalDate fechaDesembolso;
    private String cuentaDestinoDesembolso;
    private long idAnalistaAprobador;
    private String motivoRechazo;
}