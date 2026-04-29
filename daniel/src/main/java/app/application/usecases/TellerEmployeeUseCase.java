package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Account.BankAccount;
import app.domain.models.Account.enums.AccountType;
import app.domain.models.Account.enums.Currency;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.models.User.enums.UserStatus;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.bankaccount.BankAccountGetBalanceService;
import app.domain.services.bankaccount.BankAccountGetOrThrowService;
import app.domain.services.bankaccount.BankAccountOpenService;

import java.math.BigDecimal;

@Service
public class TellerEmployeeUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private BankAccountGetBalanceService bankAccountGetBalanceService;
    @Autowired
    private BankAccountGetOrThrowService bankAccountGetOrThrowService;
    @Autowired
    private BankAccountOpenService bankAccountOpenService;

    public TellerEmployeeUseCase(UserFindByIdService userFindByIdService,
                                 UserValidateRoleService userValidateRoleService,
                                 BankAccountGetBalanceService bankAccountGetBalanceService,
                                 BankAccountGetOrThrowService bankAccountGetOrThrowService,
                                 BankAccountOpenService bankAccountOpenService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.bankAccountGetBalanceService = bankAccountGetBalanceService;
        this.bankAccountGetOrThrowService = bankAccountGetOrThrowService;
        this.bankAccountOpenService = bankAccountOpenService;
    }

    public BigDecimal getAccountBalance(long requestingUserId, String accountNumber) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.TELLER_EMPLOYEE);
        return bankAccountGetBalanceService.execute(accountNumber);
    }


    public BankAccount getAccountDetail(long requestingUserId, String accountNumber) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.TELLER_EMPLOYEE);
        return bankAccountGetOrThrowService.execute(accountNumber);
    }

    public BankAccount openAccount(long requestingUserId, String clientIdentificationId,
                                   AccountType accountType, Currency currency) throws BusinessException {
        User teller = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(teller, SystemRole.TELLER_EMPLOYEE);

        // El cliente debe existir y estar activo
        User client = userFindByIdService.execute(
                getUserIdByIdentificationId(clientIdentificationId));
        if (client.getUserStatus() == UserStatus.INACTIVE)
            throw new BusinessException("Cannot open account for an inactive client");
        if (client.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException("Cannot open account for a blocked client");

        return bankAccountOpenService.execute(clientIdentificationId, accountType, currency);
    }

    private long getUserIdByIdentificationId(String identificationId) {
        throw new BusinessException("Use openAccountByUserId instead");
    }

    public BankAccount openAccountForClient(long requestingUserId, long clientUserId,
                                            AccountType accountType, Currency currency) throws BusinessException {
        User teller = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(teller, SystemRole.TELLER_EMPLOYEE);

        User client = userFindByIdService.execute(clientUserId);
        if (client.getUserStatus() == UserStatus.INACTIVE)
            throw new BusinessException("Cannot open account for an inactive client");
        if (client.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException("Cannot open account for a blocked client");

        return bankAccountOpenService.execute(client.getIdentificationId(), accountType, currency);
    }
}