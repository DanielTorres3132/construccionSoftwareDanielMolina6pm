package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import app.domain.services.RegisterLogService;
import app.domain.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TransferCreateService {
    private static final BigDecimal HIGH_AMOUNT_THRESHOLD = new BigDecimal("1000000");
    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogService registerLogService;
    private final UserService userService;

    public TransferCreateService(TransferRepositoryPort transferRepositoryPort,
                                 RegisterLogService registerLogService,
                                 UserService userService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogService = registerLogService;
        this.userService = userService;
    }

    public Transfer execute(Transfer transfer, long requestingUserId) {
        User requestingUser = userService.findById(requestingUserId);
        userService.validateAnyRole(requestingUser,
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
        registerLogService.saveLog(
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
