package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import app.domain.services.RegisterLogService;
import app.domain.services.UserService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransferApproveService {
    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogService registerLogService;
    private final UserService userService;
    private final TransferFindByIdService transferFindByIdService;

    public TransferApproveService(TransferRepositoryPort transferRepositoryPort,
                                  RegisterLogService registerLogService,
                                  UserService userService,
                                  TransferFindByIdService transferFindByIdService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogService = registerLogService;
        this.userService = userService;
        this.transferFindByIdService = transferFindByIdService;
    }

    public Transfer execute(long transferId, long approverUserId) {
        User approver = userService.findById(approverUserId);
        userService.validateRole(approver, SystemRole.COMPANY_SUPERVISOR);
        Transfer transfer = transferFindByIdService.execute(transferId);
        if (transfer.getTransferStatus() != TransferStatus.WAITING_FOR_APPROVAL)
            throw new BusinessException("Transfer with ID " + transferId + " is not waiting for approval");
        if (isExpired(transfer))
            throw new BusinessException("Transfer with ID " + transferId + " has already expired");
        transfer.setTransferStatus(TransferStatus.EXECUTED);
        transfer.setApprovalDate(LocalDateTime.now());
        transfer.setApproverUserId(approverUserId);
        Transfer saved = transferRepositoryPort.save(transfer);
        Map<String, Object> detail = new HashMap<>();
        detail.put("amount", transfer.getAmount());
        detail.put("sourceAccount", transfer.getSourceAccount());
        detail.put("destinationAccount", transfer.getDestinationAccount());
        detail.put("previousStatus", TransferStatus.WAITING_FOR_APPROVAL.name());
        detail.put("newStatus", TransferStatus.EXECUTED.name());
        detail.put("approverUserId", approverUserId);
        registerLogService.saveLog(
                "TRANSFER_APPROVED",
                approverUserId,
                approver.getSystemRole(),
                String.valueOf(transferId),
                detail
        );
        return saved;
    }

    private boolean isExpired(Transfer transfer) {
        return transfer.getCreationDate()
                .plusHours(1)
                .isBefore(LocalDateTime.now());
    }
}
