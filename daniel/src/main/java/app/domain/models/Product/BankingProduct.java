package app.domain.models.Product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class BankingProduct { 
    private String productCode; 
    private String productName; 
    private String category; 
    private boolean requiresApproval; 
    private String description; 
    private boolean active; 
}