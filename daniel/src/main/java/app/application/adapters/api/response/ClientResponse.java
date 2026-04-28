package app.application.adapters.api.response;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Client.CompanyClient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO de respuesta para datos de clientes.
 * 
 * Transporta la información de un cliente hacia la respuesta HTTP.
 * Utiliza pattern Builder para facilitar la construcción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponse {

    private Long id;

    private String name;

    private String identificationId;

    private String email;

    private String phone;

    private String address;

    private String clientType; // "NATURAL_PERSON" o "COMPANY"

    /**
     * Convierte un modelo de dominio NaturalPersonClient a respuesta.
     * 
     * @param client Cliente persona natural del dominio
     * @return DTO de respuesta
     */
    public static ClientResponse fromNaturalPersonClient(NaturalPersonClient client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getFullName())
                .identificationId(client.getIdentificationId())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .clientType("NATURAL_PERSON")
                .build();
    }

    /**
     * Convierte un modelo de dominio CompanyClient a respuesta.
     * 
     * @param client Cliente empresa del dominio
     * @return DTO de respuesta
     */
    public static ClientResponse fromCompanyClient(CompanyClient client) {
        return ClientResponse.builder()
                .id(client.getId())
                .name(client.getCompanyName())
                .identificationId(client.getNit())
                .email(client.getCompanyEmail())
                .phone(client.getCompanyPhone())
                .address(client.getFiscalAddress())
                .clientType("COMPANY")
                .build();
    }
}
