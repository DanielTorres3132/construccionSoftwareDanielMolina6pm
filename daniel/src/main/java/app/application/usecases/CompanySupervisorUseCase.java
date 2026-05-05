package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.user.UserCreateService;
import app.domain.services.user.UserActivateService;
import app.domain.services.user.UserDeactivateService;
import app.domain.services.user.UserBlockService;
import app.domain.services.user.UserFindByIdentificationIdService;
import app.domain.services.bankaccount.BankAccountFindByHolderIdService;
import app.domain.services.bankaccount.BankAccountGetOrThrowService;
import app.domain.services.transfer.TransferFindPendingApprovalService;
import app.domain.services.transfer.TransferApproveService;
import app.domain.services.transfer.TransferRejectService;
import app.domain.services.transfer.TransferFindByIdService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanySupervisorUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private UserCreateService userCreateService;
    @Autowired
    private UserActivateService userActivateService;
    @Autowired
    private UserDeactivateService userDeactivateService;
    @Autowired
    private UserBlockService userBlockService;
    @Autowired
    private UserFindByIdentificationIdService userFindByIdentificationIdService;
    @Autowired
    private BankAccountFindByHolderIdService bankAccountFindByHolderIdService;
    @Autowired
    private BankAccountGetOrThrowService bankAccountGetOrThrowService;
    @Autowired
    private TransferFindPendingApprovalService transferFindPendingApprovalService;
    @Autowired
    private TransferApproveService transferApproveService;
    @Autowired
    private TransferRejectService transferRejectService;
    @Autowired
    private TransferFindByIdService transferFindByIdService;

    public CompanySupervisorUseCase(UserFindByIdService userFindByIdService,
                                    UserValidateRoleService userValidateRoleService,
                                    UserCreateService userCreateService,
                                    UserActivateService userActivateService,
                                    UserDeactivateService userDeactivateService,
                                    UserBlockService userBlockService,
                                    UserFindByIdentificationIdService userFindByIdentificationIdService,
                                    BankAccountFindByHolderIdService bankAccountFindByHolderIdService,
                                    BankAccountGetOrThrowService bankAccountGetOrThrowService,
                                    TransferFindPendingApprovalService transferFindPendingApprovalService,
                                    TransferApproveService transferApproveService,
                                    TransferRejectService transferRejectService,
                                    TransferFindByIdService transferFindByIdService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.userCreateService = userCreateService;
        this.userActivateService = userActivateService;
        this.userDeactivateService = userDeactivateService;
        this.userBlockService = userBlockService;
        this.userFindByIdentificationIdService = userFindByIdentificationIdService;
        this.bankAccountFindByHolderIdService = bankAccountFindByHolderIdService;
        this.bankAccountGetOrThrowService = bankAccountGetOrThrowService;
        this.transferFindPendingApprovalService = transferFindPendingApprovalService;
        this.transferApproveService = transferApproveService;
        this.transferRejectService = transferRejectService;
        this.transferFindByIdService = transferFindByIdService;
    }

    public Optional<BankAccount> getCompanyAccount(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        return bankAccountFindByHolderIdService.execute(user.getIdentificationId());
    }

    public BankAccount getCompanyAccountDetail(long requestingUserId, String accountNumber) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        BankAccount account = bankAccountGetOrThrowService.execute(accountNumber);
        if (!account.getHolderId().equals(user.getIdentificationId()))
            throw new BusinessException("Account " + accountNumber + " does not belong to your company");
        return account;
    }


    public List<Transfer> getPendingTransfersForApproval(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        return transferFindPendingApprovalService.execute();
    }

    public Transfer getTransferDetail(long requestingUserId, long transferId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        Transfer transfer = transferFindByIdService.execute(transferId);
        // Verify the transfer belongs to a company account (not direct validation of supervisor's company yet)
        return transfer;
    }

    public Transfer approveTransfer(long requestingUserId, long transferId, String approvalNotes) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        return transferApproveService.execute(transferId, approvalNotes);
    }

    public Transfer rejectTransfer(long requestingUserId, long transferId, String rejectionReason) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_SUPERVISOR);
        return transferRejectService.execute(transferId, rejectionReason);
    }

    public User createOperativeUser(long requestingUserId, String username, String email,
                                    String firstName, String lastName, String identificationId,
                                    String identificationType) throws BusinessException {
        User supervisor = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(supervisor, SystemRole.COMPANY_SUPERVISOR);
        
        return userCreateService.execute(username, email, firstName, lastName, 
                                        identificationId, identificationType, SystemRole.COMPANY_EMPLOYEE);
    }

    public User activateOperativeUser(long requestingUserId, long operativeUserId) throws BusinessException {
        User supervisor = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(supervisor, SystemRole.COMPANY_SUPERVISOR);
        
        return userActivateService.execute(operativeUserId);
    }

    public User deactivateOperativeUser(long requestingUserId, long operativeUserId) throws BusinessException {
        User supervisor = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(supervisor, SystemRole.COMPANY_SUPERVISOR);
        
        return userDeactivateService.execute(operativeUserId);
    }

    public User blockOperativeUser(long requestingUserId, long operativeUserId, String blockReason) throws BusinessException {
        User supervisor = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(supervisor, SystemRole.COMPANY_SUPERVISOR);
        
        return userBlockService.execute(operativeUserId, blockReason);
    }

    public User getOperativeUserDetail(long requestingUserId, long operativeUserId) throws BusinessException {
        User supervisor = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(supervisor, SystemRole.COMPANY_SUPERVISOR);
        
        User operativeUser = userFindByIdService.execute(operativeUserId);
        // Verify the operative user belongs to the supervisor's company
        if (!operativeUser.getIdentificationId().equals(supervisor.getIdentificationId()))
            throw new BusinessException("User with ID " + operativeUserId + " does not belong to your company");
        
        return operativeUser;
    }
}
