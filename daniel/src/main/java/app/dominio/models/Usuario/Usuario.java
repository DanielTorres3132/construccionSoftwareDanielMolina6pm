package app.dominio.models.Usuario;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import app.dominio.models.Usuario.enums.EstadoUsuario;
import app.dominio.models.Usuario.enums.RolSistema;

@Setter
@Getter
@NoArgsConstructor
public class Usuario {
    private long id;
    private String nombreCompleto;
    private String idIdentificacion;
    private String correoElectronico;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String direccion;
    private RolSistema rolSistema;
    private EstadoUsuario estadoUsuario;
    private String username;
    private String password;
}