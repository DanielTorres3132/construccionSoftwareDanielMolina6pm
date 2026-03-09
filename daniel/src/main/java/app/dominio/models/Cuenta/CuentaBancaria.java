package app.dominio.models.Cuenta;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import app.dominio.models.Cuenta.enums.EstadoCuenta;
import app.dominio.models.Cuenta.enums.TipoCuenta;

@Setter
@Getter
@NoArgsConstructor
public class CuentaBancaria {
    private String numeroCuenta;
    private TipoCuenta tipoCuenta;
    private String idTitular;
    private double saldoActual;
    private String moneda;
    private EstadoCuenta estadoCuenta;
    private LocalDate fechaApertura;
}