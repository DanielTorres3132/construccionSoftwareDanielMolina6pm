package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.Log.RegisterLog;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.companyclient.CompanyClientFindByIdService;
import app.domain.services.naturalpersonclient.NaturalPersonClientFindByIdService;
import app.domain.services.loan.LoanApproveService;
import app.domain.services.loan.LoanRejectService;
import app.domain.services.loan.LoanGetOrThrowService;
import app.domain.services.loan.LoanDisburseService;
import app.domain.services.registerlog.RegisterLogFindByUserIdService;
import app.domain.services.registerlog.RegisterLogFindByOperationTypeService;
import app.domain.services.registerlog.RegisterLogFindByAffectedProductIdService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InternalAnalystUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private CompanyClientFindByIdService companyClientFindByIdService;
    @Autowired
    private NaturalPersonClientFindByIdService naturalPersonClientFindByIdService;
    @Autowired
    private LoanApproveService loanApproveService;
    @Autowired
    private LoanRejectService loanRejectService;
    @Autowired
    private LoanGetOrThrowService loanGetOrThrowService;
    @Autowired
    private LoanDisburseService loanDisburseService;
    @Autowired
    private RegisterLogFindByUserIdService registerLogFindByUserIdService;
    @Autowired
    private RegisterLogFindByOperationTypeService registerLogFindByOperationTypeService;
    @Autowired
    private RegisterLogFindByAffectedProductIdService registerLogFindByAffectedProductIdService;

    public InternalAnalystUseCase(UserFindByIdService userFindByIdService,
                                  UserValidateRoleService userValidateRoleService,
                                  CompanyClientFindByIdService companyClientFindByIdService,
                                  NaturalPersonClientFindByIdService naturalPersonClientFindByIdService,
                                  LoanApproveService loanApproveService,
                                  LoanRejectService loanRejectService,
                                  LoanGetOrThrowService loanGetOrThrowService,
                                  LoanDisburseService loanDisburseService,
                                  RegisterLogFindByUserIdService registerLogFindByUserIdService,
                                  RegisterLogFindByOperationTypeService registerLogFindByOperationTypeService,
                                  RegisterLogFindByAffectedProductIdService registerLogFindByAffectedProductIdService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.companyClientFindByIdService = companyClientFindByIdService;
        this.naturalPersonClientFindByIdService = naturalPersonClientFindByIdService;
        this.loanApproveService = loanApproveService;
        this.loanRejectService = loanRejectService;
        this.loanGetOrThrowService = loanGetOrThrowService;
        this.loanDisburseService = loanDisburseService;
        this.registerLogFindByUserIdService = registerLogFindByUserIdService;
        this.registerLogFindByOperationTypeService = registerLogFindByOperationTypeService;
        this.registerLogFindByAffectedProductIdService = registerLogFindByAffectedProductIdService;
    }

    public CompanyClient findCompanyClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return companyClientFindByIdService.execute(clientId);
    }

    public NaturalPersonClient findNaturalPersonClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return naturalPersonClientFindByIdService.execute(clientId);
    }


    public Loan getLoanDetail(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return loanGetOrThrowService.execute(loanId);
    }

    public Loan approveLoan(long requestingUserId, long loanId, BigDecimal approvedAmount, BigDecimal interestRate) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return loanApproveService.execute(loanId, requestingUserId, approvedAmount, interestRate);
    }

    public Loan disburseLoan(long requestingUserId, long loanId, String disbursementAccount) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return loanDisburseService.execute(loanId, disbursementAccount);
    }

    public Loan rejectLoan(long requestingUserId, long loanId, String reason) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return loanRejectService.execute(loanId, requestingUserId, reason);
    }

    public List<RegisterLog> getAuditLogByUser(long requestingUserId, long userId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return registerLogFindByUserIdService.execute(userId);
    }

    public List<RegisterLog> getAuditLogByOperationType(long requestingUserId, String operationType) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return registerLogFindByOperationTypeService.execute(operationType);
    }

    public List<RegisterLog> getAuditLogByProduct(long requestingUserId, long productId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.INTERNAL_ANALYST);
        return registerLogFindByAffectedProductIdService.execute(String.valueOf(productId));
    }
}
