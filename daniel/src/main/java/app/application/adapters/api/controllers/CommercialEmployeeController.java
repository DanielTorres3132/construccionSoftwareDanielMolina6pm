package app.application.adapters.api.controllers;

import app.application.adapters.api.request.CompanyClientCreateRequest;
import app.application.adapters.api.request.NaturalPersonClientCreateRequest;
import app.application.adapters.api.response.CompanyClientResponse;
import app.application.adapters.api.response.NaturalPersonClientResponse;
import app.application.usecases.CommercialEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/commercial-employee")
@Validated
public class CommercialEmployeeController {
    private final CommercialEmployeeUseCase useCase;

    public CommercialEmployeeController(CommercialEmployeeUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/company-client")
    public ResponseEntity<CompanyClientResponse> createCompanyClient(
            @RequestParam long requestingUserId,
            @Valid @RequestBody CompanyClientCreateRequest request) throws BusinessException {
        var client = useCase.createCompanyClient(
                requestingUserId,
                request.getNit(),
                request.getCompanyName(),
                request.getAddress(),
                request.getPhone(),
                request.getEmail(),
                request.getLegalRepresentativeId()
        );
        return ResponseEntity.ok(toCompanyClientResponse(client));
    }

    @PostMapping("/natural-person-client")
    public ResponseEntity<NaturalPersonClientResponse> createNaturalPersonClient(
            @RequestParam long requestingUserId,
            @Valid @RequestBody NaturalPersonClientCreateRequest request) throws BusinessException {
        var client = useCase.createNaturalPersonClient(
                requestingUserId,
                request.getIdentificationId(),
                request.getFullName(),
                request.getAddress(),
                request.getPhone(),
                request.getEmail(),
                request.getBirthDate()
        );
        return ResponseEntity.ok(toNaturalPersonClientResponse(client));
    }

     // --- Mappers ---
    private CompanyClientResponse toCompanyClientResponse(CompanyClient client) {
        CompanyClientResponse response = new CompanyClientResponse();
        response.setId(client.getId());
        response.setNit(client.getNit());
        response.setCompanyName(client.getCompanyName());
        response.setAddress(client.getFiscalAddress());
        response.setPhone(client.getCompanyPhone());
        response.setEmail(client.getCompanyEmail());
        response.setLegalRepresentativeId(client.getLegalRepresentativeId());
        return response;
    }

    private NaturalPersonClientResponse toNaturalPersonClientResponse(NaturalPersonClient client) {
        NaturalPersonClientResponse response = new NaturalPersonClientResponse();
        response.setId(client.getId());
        response.setIdentificationId(client.getIdentificationId());
        response.setFullName(client.getFullName());
        response.setAddress(client.getAddress());
        response.setPhone(client.getPhone());
        response.setEmail(client.getEmail());
        response.setBirthDate(client.getBirthDate());
        return response;
    }
}
