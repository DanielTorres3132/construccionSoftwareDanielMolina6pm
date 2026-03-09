package app.dominio.models.Usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ClienteEmpresa extends Usuario {
    private String razonSocial;
    private String nit;
    private String correoEmpresa;
    private String telefonoEmpresa;
    private String direccionFiscal;
    private Usuario representanteLegal;
}