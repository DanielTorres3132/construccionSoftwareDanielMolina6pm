package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class TransferFindByIdService {
    private final TransferRepositoryPort transferRepositoryPort;

    public TransferFindByIdService(TransferRepositoryPort transferRepositoryPort) {
        this.transferRepositoryPort = transferRepositoryPort;
    }

    public Transfer execute(long id) {
        return transferRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Transfer with ID " + id + " not found"));
    }
}
