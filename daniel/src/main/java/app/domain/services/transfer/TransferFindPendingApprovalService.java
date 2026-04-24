package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.ports.TransferRepositoryPort;
import java.util.List;

public class TransferFindPendingApprovalService {
    private final TransferRepositoryPort transferRepositoryPort;

    public TransferFindPendingApprovalService(TransferRepositoryPort transferRepositoryPort) {
        this.transferRepositoryPort = transferRepositoryPort;
    }

    public List<Transfer> execute() {
        return transferRepositoryPort.findByTransferStatus(
                TransferStatus.WAITING_FOR_APPROVAL);
    }
}
