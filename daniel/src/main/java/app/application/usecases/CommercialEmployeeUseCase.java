package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.companyclient.CompanyClientCreateService;
import app.domain.services.companyclient.CompanyClientFindByIdService;
import app.domain.services.naturalpersonclient.NaturalPersonClientCreateService;
import app.domain.services.naturalpersonclient.NaturalPersonClientFindByIdService;
import app.domain.services.loan.LoanApproveService;
import app.domain.services.loan.LoanRejectService;
import app.domain.services.loan.LoanGetOrThrowService;
import app.domain.services.transfer.TransferApproveService;
import app.domain.services.transfer.TransferRejectService;
import app.domain.services.transfer.TransferFindPendingApprovalService;

import java.util.List;

@Service
public class CommercialEmployeeUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private CompanyClientCreateService companyClientCreateService;
    @Autowired
    private CompanyClientFindByIdService companyClientFindByIdService;
    @Autowired
    private NaturalPersonClientCreateService naturalPersonClientCreateService;
    @Autowired
    private NaturalPersonClientFindByIdService naturalPersonClientFindByIdService;
    @Autowired
    private LoanApproveService loanApproveService;
    @Autowired
    private LoanRejectService loanRejectService;
    @Autowired
    private LoanGetOrThrowService loanGetOrThrowService;
    @Autowired
    private TransferApproveService transferApproveService;
    @Autowired
    private TransferRejectService transferRejectService;
    @Autowired
    private TransferFindPendingApprovalService transferFindPendingApprovalService;

    public CommercialEmployeeUseCase(UserFindByIdService userFindByIdService,
                                     UserValidateRoleService userValidateRoleService,
                                     CompanyClientCreateService companyClientCreateService,
                                     CompanyClientFindByIdService companyClientFindByIdService,
                                     NaturalPersonClientCreateService naturalPersonClientCreateService,
                                     NaturalPersonClientFindByIdService naturalPersonClientFindByIdService,
                                     LoanApproveService loanApproveService,
                                     LoanRejectService loanRejectService,
                                     LoanGetOrThrowService loanGetOrThrowService,
                                     TransferApproveService transferApproveService,
                                     TransferRejectService transferRejectService,
                                     TransferFindPendingApprovalService transferFindPendingApprovalService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.companyClientCreateService = companyClientCreateService;
        this.companyClientFindByIdService = companyClientFindByIdService;
        this.naturalPersonClientCreateService = naturalPersonClientCreateService;
        this.naturalPersonClientFindByIdService = naturalPersonClientFindByIdService;
        this.loanApproveService = loanApproveService;
        this.loanRejectService = loanRejectService;
        this.loanGetOrThrowService = loanGetOrThrowService;
        this.transferApproveService = transferApproveService;
        this.transferRejectService = transferRejectService;
        this.transferFindPendingApprovalService = transferFindPendingApprovalService;
    }

    public CompanyClient createCompanyClient(long requestingUserId, String nit, String companyName,
                                             String address, String phone, String email) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return companyClientCreateService.execute(nit, companyName, address, phone, email);
    }

    public NaturalPersonClient createNaturalPersonClient(long requestingUserId, String identificationId,
                                                         String firstName, String lastName, String address,
                                                         String phone, String email) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return naturalPersonClientCreateService.execute(identificationId, firstName, lastName, address, phone, email);
    }

    public CompanyClient findCompanyClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return companyClientFindByIdService.execute(clientId);
    }

    public NaturalPersonClient findNaturalPersonClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return naturalPersonClientFindByIdService.execute(clientId);
    }

    public Loan approveLoan(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return loanApproveService.execute(loanId);
    }

    public Loan rejectLoan(long requestingUserId, long loanId, String reason) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return loanRejectService.execute(loanId, reason);
    }

    public Loan getLoanDetail(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return loanGetOrThrowService.execute(loanId);
    }

    public Transfer approveTransfer(long requestingUserId, long transferId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return transferApproveService.execute(transferId);
    }

    public Transfer rejectTransfer(long requestingUserId, long transferId, String reason) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return transferRejectService.execute(transferId, reason);
    }

    public List<Transfer> getPendingTransfers(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return transferFindPendingApprovalService.execute();
    }
}