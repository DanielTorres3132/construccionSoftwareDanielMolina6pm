package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.services.registerlog.RegisterLogSaveService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferVerifyExpirationService {
    private static final int APPROVAL_WINDOW_HOURS = 1;
    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogSaveService registerLogSaveService;

    public TransferVerifyExpirationService(TransferRepositoryPort transferRepositoryPort,
                                           RegisterLogSaveService registerLogSaveService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogSaveService = registerLogSaveService;
    }

    public void execute() {
        LocalDateTime expirationThreshold = LocalDateTime.now().minusHours(APPROVAL_WINDOW_HOURS);
        List<Transfer> expiredTransfers = transferRepositoryPort.findExpiredTransfers(expirationThreshold);
        for (Transfer transfer : expiredTransfers) {
            transfer.setTransferStatus(TransferStatus.EXPIRED);
            transferRepositoryPort.save(transfer);
            Map<String, Object> detail = new HashMap<>();
            detail.put("reason", "Expired due to lack of approval within the established time");
            detail.put("expirationDateTime", LocalDateTime.now().toString());
            detail.put("creatorUserId", transfer.getCreatorUserId());
            detail.put("amount", transfer.getAmount());
            detail.put("sourceAccount", transfer.getSourceAccount());
            detail.put("destinationAccount", transfer.getDestinationAccount());
            registerLogSaveService.execute(
                    "TRANSFER_EXPIRED",
                    transfer.getCreatorUserId(),
                    SystemRole.COMPANY_EMPLOYEE,
                    String.valueOf(transfer.getId()),
                    detail
            );
        }
    }
}
