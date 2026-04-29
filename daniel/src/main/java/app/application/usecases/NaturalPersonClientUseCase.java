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
import app.domain.services.transfer.TransferCreateService;
import app.domain.services.transfer.TransferFindByCreatorUserIdService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class NaturalPersonClientUseCase {

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
    private TransferCreateService transferCreateService;
    @Autowired
    private TransferFindByCreatorUserIdService transferFindByCreatorUserIdService;

    public NaturalPersonClientUseCase(UserFindByIdService userFindByIdService,
                                      UserValidateRoleService userValidateRoleService,
                                      BankAccountFindByHolderIdService bankAccountFindByHolderIdService,
                                      BankAccountGetOrThrowService bankAccountGetOrThrowService,
                                      LoanCreateRequestService loanCreateRequestService,
                                      LoanGetOrThrowService loanGetOrThrowService,
                                      TransferCreateService transferCreateService,
                                      TransferFindByCreatorUserIdService transferFindByCreatorUserIdService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.bankAccountFindByHolderIdService = bankAccountFindByHolderIdService;
        this.bankAccountGetOrThrowService = bankAccountGetOrThrowService;
        this.loanCreateRequestService = loanCreateRequestService;
        this.loanGetOrThrowService = loanGetOrThrowService;
        this.transferCreateService = transferCreateService;
        this.transferFindByCreatorUserIdService = transferFindByCreatorUserIdService;
    }

    public Optional<BankAccount> getOwnAccount(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        return bankAccountFindByHolderIdService.execute(user.getIdentificationId());
    }

    public BankAccount getOwnAccountDetail(long requestingUserId, String accountNumber) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        BankAccount account = bankAccountGetOrThrowService.execute(accountNumber);
        if (!account.getHolderId().equals(user.getIdentificationId()))
            throw new BusinessException("Account " + accountNumber + " does not belong to you");
        return account;
    }

    public Loan getOwnLoan(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        Loan loan = loanGetOrThrowService.execute(loanId);
        if (!loan.getApplicantClientId().equals(user.getIdentificationId()))
            throw new BusinessException("Loan with ID " + loanId + " does not belong to you");
        return loan;
    }

    public List<Transfer> getOwnTransferHistory(long requestingUserId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        return transferFindByCreatorUserIdService.execute(requestingUserId);
    }

    public Loan createLoanRequest(long requestingUserId, LoanType loanType,
                                  BigDecimal requestedAmount, int termMonths) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        return loanCreateRequestService.execute(
                user.getIdentificationId(), loanType, requestedAmount, termMonths);
    }

    public Transfer createTransfer(long requestingUserId, String sourceAccount,
                                   String destinationAccount, BigDecimal amount) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.NATURAL_PERSON_CLIENT);
        BankAccount source = bankAccountGetOrThrowService.execute(sourceAccount);
        if (!source.getHolderId().equals(user.getIdentificationId()))
            throw new BusinessException("Account " + sourceAccount + " does not belong to you");
        Transfer transfer = new Transfer();
        transfer.setSourceAccount(sourceAccount);
        transfer.setDestinationAccount(destinationAccount);
        transfer.setAmount(amount);
        return transferCreateService.execute(transfer, requestingUserId);
    }
}