package app.application.usecases;

import app.application.adapters.api.request.OpenBankAccountRequest;
import app.domain.models.Account.BankAccount;
import app.domain.models.User.User;
import app.domain.services.bankaccount.BankAccountOpenService;
import app.domain.services.user.UserFindByIdService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenBankAccountUseCase {

    private final BankAccountOpenService bankAccountOpenService;
    private final UserFindByIdService userFindByIdService;

    @Autowired
    public OpenBankAccountUseCase(BankAccountOpenService bankAccountOpenService,
                                  UserFindByIdService userFindByIdService) {
        this.bankAccountOpenService = bankAccountOpenService;
        this.userFindByIdService = userFindByIdService;
    }

    public BankAccount execute(OpenBankAccountRequest request) {
        User holder = userFindByIdService.execute(request.getHolderId());

        BankAccount newAccount = bankAccountOpenService.execute(
                holder.getIdentificationId(),
                request.getAccountType(),
                request.getCurrency()
        );

        return newAccount;
    }
}