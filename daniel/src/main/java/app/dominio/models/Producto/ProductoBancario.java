package app.dominio.models.Producto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductoBancario {
    private String codigoProducto;
    private String nombreProducto;
    private String categoria;
    private boolean requiereAprobacion;
    private String descripcion;
    private boolean activo;
}