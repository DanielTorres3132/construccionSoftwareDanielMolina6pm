package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import app.domain.services.registerlog.RegisterLogSaveService;
import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransferApproveService {
    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogSaveService registerLogSaveService;
    private final UserFindByIdService userFindByIdService;
    private final UserValidateRoleService userValidateRoleService;
    private final TransferFindByIdService transferFindByIdService;

    public TransferApproveService(TransferRepositoryPort transferRepositoryPort,
                                  RegisterLogSaveService registerLogSaveService,
                                  UserFindByIdService userFindByIdService,
                                  UserValidateRoleService userValidateRoleService,
                                  TransferFindByIdService transferFindByIdService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogSaveService = registerLogSaveService;
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.transferFindByIdService = transferFindByIdService;
    }

    public Transfer execute(long transferId, long approverUserId) {
        User approver = userFindByIdService.execute(approverUserId);
        userValidateRoleService.execute(approver, SystemRole.COMPANY_SUPERVISOR);
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
        registerLogSaveService.execute(
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
