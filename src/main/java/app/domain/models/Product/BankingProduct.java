package app.domain.models.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BankingProduct { //producto bancario
    private String productCode; //codigo del producto
    private String productName; //nombre del producto
    private String category; //categoria 
    private boolean requiresApproval; //requiere aprobacion o no
    private String description; //descripcion del producto
    private boolean active; //activo o no
}