package app.application.adapters.api.controllers;

import app.application.usecases.CreateNaturalPersonClientUseCase;
import app.application.adapters.api.request.CreateNaturalPersonClientRequest;
import app.application.adapters.api.response.ClientResponse;
import app.domain.models.Client.NaturalPersonClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final CreateNaturalPersonClientUseCase createNaturalPersonClientUseCase;

    @Autowired
    public ClientController(CreateNaturalPersonClientUseCase createNaturalPersonClientUseCase) {
        this.createNaturalPersonClientUseCase = createNaturalPersonClientUseCase;
    }

    @PostMapping("/natural-person")
    public ResponseEntity<ClientResponse> createNaturalPersonClient(
            @Valid @RequestBody CreateNaturalPersonClientRequest request) {

        NaturalPersonClient client = mapToDomain(request);

        NaturalPersonClient createdClient = createNaturalPersonClientUseCase.execute(client);

        ClientResponse response = ClientResponse.fromNaturalPersonClient(createdClient);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable long id) {
        return ResponseEntity.ok(new ClientResponse());
    }

    private NaturalPersonClient mapToDomain(CreateNaturalPersonClientRequest request) {
        NaturalPersonClient client = new NaturalPersonClient();
        client.setFullName(request.getFullName());
        client.setIdentificationId(request.getIdentificationId());
        client.setEmail(request.getEmail());
        client.setPhone(request.getPhone());
        client.setAddress(request.getAddress());
        client.setBirthDate(request.getBirthDate());
        return client;
    }
}