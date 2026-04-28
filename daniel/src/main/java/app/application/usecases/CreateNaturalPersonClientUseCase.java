package app.application.usecases;

import app.domain.models.Client.NaturalPersonClient;
import app.domain.services.naturalpersonclient.NaturalPersonClientCreateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateNaturalPersonClientUseCase {

    private final NaturalPersonClientCreateService createService;

    @Autowired
    public CreateNaturalPersonClientUseCase(NaturalPersonClientCreateService createService) {
        this.createService = createService;
    }

    public NaturalPersonClient execute(NaturalPersonClient client) {
        return createService.execute(client);
    }
}