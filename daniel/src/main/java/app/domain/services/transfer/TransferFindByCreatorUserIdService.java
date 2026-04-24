package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.ports.TransferRepositoryPort;
import java.util.List;

public class TransferFindByCreatorUserIdService {
    private final TransferRepositoryPort transferRepositoryPort;

    public TransferFindByCreatorUserIdService(TransferRepositoryPort transferRepositoryPort) {
        this.transferRepositoryPort = transferRepositoryPort;
    }

    public List<Transfer> execute(long creatorUserId) {
        return transferRepositoryPort.findByCreatorUserId(creatorUserId);
    }
}
