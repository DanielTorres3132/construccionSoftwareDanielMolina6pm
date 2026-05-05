package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanType;
import app.domain.models.Transfer.Transfer;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.bankaccount.BankAccountFindByHolderIdService;
import app.domain.services.bankaccount.BankAccountGetOrThrowService;
import app.domain.services.loan.LoanCreateRequestService;
import app.domain.services.loan.LoanGetOrThrowService;
import app.domain.services.loan.LoanApproveService;
import app.domain.services.transfer.TransferCreateService;
import app.domain.services.transfer.TransferFindByCreatorUserIdService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyEmployeeUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private BankAccountFindByHolderIdService bankAccountFindByHolderIdService;
    @Autowired
    private BankAccountGetOrThrowService bankAccountGetOrThrowService;
    @Autowired
    private LoanCreateRequestService loanCreateRequestService;
    @Autowired
    private LoanGetOrThrowService loanGetOrThrowService;
    @Autowired
    private LoanApproveService loanApproveService;
    @Autowired
    private TransferCreateService transferCreateService;
    @Autowired
    private TransferFindByCreatorUserIdService transferFindByCreatorUserIdService;

    public CompanyEmployeeUseCase(UserFindByIdService userFindByIdService,
            UserValidateRoleService userValidateRoleService,
            BankAccountFindByHolderIdService bankAccountFindByHolderIdService,
            BankAccountGetOrThrowService bankAccountGetOrThrowService,
            LoanCreateRequestService loanCreateRequestService,
            LoanGetOrThrowService loanGetOrThrowService,
            LoanApproveService loanApproveService,
            TransferCreateService transferCreateService,
            TransferFindByCreatorUserIdService transferFindByCreatorUserIdService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.bankAccountFindByHolderIdService = bankAccountFindByHolderIdService;
        this.bankAccountGetOrThrowService = bankAccountGetOrThrowService;
        this.loanCreateRequestService = loanCreateRequestService;
        this.loanGetOrThrowService = loanGetOrThrowService;
        this.loanApproveService = loanApproveService;
        this.transferCreateService = transferCreateService;
        this.transferFindByCreatorUserIdService = transferFindByCreatorUserIdService;
    }

    public Optional<BankAccount> getCompanyAccount(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        return bankAccountFindByHolderIdService.execute(user.getIdentificationId());
    }

    public BankAccount getCompanyAccountDetail(long requestingUserId, String accountNumber) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        BankAccount account = bankAccountGetOrThrowService.execute(accountNumber);
        if (!account.getHolderId().equals(user.getIdentificationId()))
            throw new BusinessException("Account " + accountNumber + " does not belong to your company");
        return account;
    }

    public Loan getCompanyLoan(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        Loan loan = loanGetOrThrowService.execute(loanId);
        if (!loan.getApplicantClientId().equals(user.getIdentificationId()))
            throw new BusinessException("Loan with ID " + loanId + " does not belong to your company");
        return loan;
    }

    public List<Transfer> getCompanyTransferHistory(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        return transferFindByCreatorUserIdService.execute(requestingUserId);
    }

    public Loan createLoanRequest(long requestingUserId, LoanType loanType,
            BigDecimal requestedAmount, int termMonths) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        return loanCreateRequestService.execute(
                user.getIdentificationId(), loanType, requestedAmount, termMonths);
    }

    public Loan approveLoan(long requestingUserId, long loanId,
            BigDecimal approvedAmount, BigDecimal interestRate) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);
        return loanApproveService.execute(loanId, requestingUserId, approvedAmount, interestRate);
    }

    public Transfer createTransfer(long requestingUserId, String sourceAccountNumber,
            String destinationAccountNumber, BigDecimal amount,
            String description) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMPANY_EMPLOYEE);

        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccountNumber);
        transfer.setDestinationAccount(destinationAccountNumber);
        transfer.setAmount(amount);

        return transferCreateService.execute(transfer, requestingUserId);
    }
}