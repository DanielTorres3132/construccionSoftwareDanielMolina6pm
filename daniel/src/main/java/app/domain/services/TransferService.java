package app.domain.services;

import app.domain.models.Transfer.Transfer;
import app.domain.models.Transfer.enums.TransferStatus;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferService {

    private static final int APPROVAL_WINDOW_HOURS = 1;
    private static final BigDecimal HIGH_AMOUNT_THRESHOLD = new BigDecimal("1000000");

    private final TransferRepositoryPort transferRepositoryPort;
    private final RegisterLogService registerLogService;
    private final UserService userService;

    public TransferService(TransferRepositoryPort transferRepositoryPort,
                           RegisterLogService registerLogService,
                           UserService userService) {
        this.transferRepositoryPort = transferRepositoryPort;
        this.registerLogService = registerLogService;
        this.userService = userService;
    }

    // ─── Crear transferencia ──────────────────────────────────────────────────

    public Transfer createTransfer(Transfer transfer, long requestingUserId) {
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

    // ─── Aprobar transferencia ────────────────────────────────────────────────

    public Transfer approveTransfer(long transferId, long approverUserId) {
        User approver = userService.findById(approverUserId);
        userService.validateRole(approver, SystemRole.COMPANY_SUPERVISOR);

        Transfer transfer = findById(transferId);

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

    // ─── Rechazar transferencia ───────────────────────────────────────────────

    public Transfer rejectTransfer(long transferId, long approverUserId, String rejectionReason) {
        User approver = userService.findById(approverUserId);
        userService.validateRole(approver, SystemRole.COMPANY_SUPERVISOR);

        if (rejectionReason == null || rejectionReason.isBlank())
            throw new BusinessException("Rejection reason is required");

        Transfer transfer = findById(transferId);

        if (transfer.getTransferStatus() != TransferStatus.WAITING_FOR_APPROVAL)
            throw new BusinessException("Transfer with ID " + transferId + " is not waiting for approval");

        if (isExpired(transfer))
            throw new BusinessException("Transfer with ID " + transferId + " has already expired");

        transfer.setTransferStatus(TransferStatus.REJECTED);
        transfer.setRejectionReason(rejectionReason);
        transfer.setApproverUserId(approverUserId);

        Transfer saved = transferRepositoryPort.save(transfer);

        Map<String, Object> detail = new HashMap<>();
        detail.put("amount", transfer.getAmount());
        detail.put("sourceAccount", transfer.getSourceAccount());
        detail.put("destinationAccount", transfer.getDestinationAccount());
        detail.put("previousStatus", TransferStatus.WAITING_FOR_APPROVAL.name());
        detail.put("newStatus", TransferStatus.REJECTED.name());
        detail.put("rejectionReason", rejectionReason);
        detail.put("approverUserId", approverUserId);

        registerLogService.saveLog(
                "TRANSFER_REJECTED",
                approverUserId,
                approver.getSystemRole(),
                String.valueOf(transferId),
                detail
        );

        return saved;
    }

    // ─── Verificar vencimiento ────────────────────────────────────────────────

    public void verifyExpiration() {
        LocalDateTime expirationThreshold = LocalDateTime.now()
                .minusHours(APPROVAL_WINDOW_HOURS);
        List<Transfer> expiredTransfers = transferRepositoryPort
                .findExpiredTransfers(expirationThreshold);

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

            registerLogService.saveLog(
                    "TRANSFER_EXPIRED",
                    transfer.getCreatorUserId(),
                    SystemRole.COMPANY_EMPLOYEE,
                    String.valueOf(transfer.getId()),
                    detail
            );
        }
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public Transfer findById(long id) {
        return transferRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException(
                        "Transfer with ID " + id + " not found"));
    }

    public List<Transfer> findByCreatorUserId(long creatorUserId) {
        return transferRepositoryPort.findByCreatorUserId(creatorUserId);
    }

    public List<Transfer> findBySourceAccount(String sourceAccount) {
        if (sourceAccount == null || sourceAccount.isBlank())
            throw new BusinessException("Source account is required");
        return transferRepositoryPort.findBySourceAccount(sourceAccount);
    }

    public List<Transfer> findPendingApproval() {
        return transferRepositoryPort.findByTransferStatus(
                TransferStatus.WAITING_FOR_APPROVAL);
    }

    // ─── Validaciones internas ────────────────────────────────────────────────

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

    private boolean isExpired(Transfer transfer) {
        return transfer.getCreationDate()
                .plusHours(APPROVAL_WINDOW_HOURS)
                .isBefore(LocalDateTime.now());
    }
}