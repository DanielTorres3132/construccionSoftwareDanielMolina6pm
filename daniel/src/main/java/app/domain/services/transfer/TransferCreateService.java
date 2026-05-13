package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import app.domain.services.registerlog.RegisterLogSaveService;
import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateAnyRoleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class TransferCreateService {
    private static final BigDecimal HIGH_AMOUNT_THRESHOLD = new BigDecimal("1000000");
    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogSaveService registerLogSaveService;
    private final UserFindByIdService userFindByIdService;
    private final UserValidateAnyRoleService userValidateAnyRoleService;

    public TransferCreateService(TransferRepositoryPort transferRepositoryPort,
                                 RegisterLogSaveService registerLogSaveService,
                                 UserFindByIdService userFindByIdService,
                                 UserValidateAnyRoleService userValidateAnyRoleService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogSaveService = registerLogSaveService;
        this.userFindByIdService = userFindByIdService;
        this.userValidateAnyRoleService = userValidateAnyRoleService;
    }

    public Transfer execute(Transfer transfer, long requestingUserId) {
        User requestingUser = userFindByIdService.execute(requestingUserId);
        userValidateAnyRoleService.execute(requestingUser,
                SystemRole.COMPANY_EMPLOYEE, SystemRole.COMPANY_SUPERVISOR);
        validateTransferFields(transfer);
        transfer.setCreationDate(LocalDateTime.now());
        transfer.setCreatorUserId(requestingUserId);
        boolean requiresApproval = transfer.getAmount()
                .compareTo(HIGH_AMOUNT_THRESHOLD) >= 0;
        transfer.setTransferStatus(requiresApproval
                ? TransferStatus.WAITING_FOR_APPROVAL
                : TransferStatus.EXECUTED);
        Transfer saved = transferRepositoryPort.save(transfer);
        Map<String, Object> detail = new HashMap<>();
        detail.put("amount", transfer.getAmount());
        detail.put("sourceAccount", transfer.getSourceAccount());
        detail.put("destinationAccount", transfer.getDestinationAccount());
        detail.put("status", saved.getTransferStatus().name());
        detail.put("requiresApproval", requiresApproval);
        registerLogSaveService.execute(
                "TRANSFER_CREATED",
                requestingUserId,
                requestingUser.getSystemRole(),
                String.valueOf(saved.getId()),
                detail
        );
        return saved;
    }

    private void validateTransferFields(Transfer transfer) {
        if (transfer.getSourceAccount() == null || transfer.getSourceAccount().isBlank())
            throw new BusinessException("Source account is required");
        if (transfer.getDestinationAccount() == null || transfer.getDestinationAccount().isBlank())
            throw new BusinessException("Destination account is required");
        if (transfer.getSourceAccount().equals(transfer.getDestinationAccount()))
            throw new BusinessException("Source and destination accounts must be different");
        if (transfer.getAmount() == null || transfer.getAmount().compareTo(BigDecimal.ZERO) <= 0)
            throw new BusinessException("Transfer amount must be greater than zero");
    }
}
