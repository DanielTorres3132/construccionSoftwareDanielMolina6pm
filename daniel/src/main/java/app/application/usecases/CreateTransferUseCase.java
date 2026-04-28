package app.application.usecases;

import app.application.adapters.api.request.CreateTransferRequest;
import app.domain.models.Transfer.Transfer;
import app.domain.models.Account.BankAccount;
import app.domain.services.transfer.TransferCreateService;
import app.domain.services.bankaccount.BankAccountFindByAccountNumberService;
import app.domain.Exceptions.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateTransferUseCase {

    private final TransferCreateService transferCreateService;
    private final BankAccountFindByAccountNumberService findByAccountNumberService;

    @Autowired
    public CreateTransferUseCase(TransferCreateService transferCreateService,
            BankAccountFindByAccountNumberService findByAccountNumberService) {
        this.transferCreateService = transferCreateService;
        this.findByAccountNumberService = findByAccountNumberService;
    }

    public Transfer execute(CreateTransferRequest request, long requestingUserId) {
        if (request.getSourceAccount().equals(request.getDestinationAccount())) {
            throw new BusinessException("Source and destination accounts cannot be the same");
        }

        Transfer transfer = new Transfer();
        transfer.setSourceAccount(request.getSourceAccount());
        transfer.setDestinationAccount(request.getDestinationAccount());
        transfer.setAmount(request.getAmount());

        BankAccount sourceAccount = findByAccountNumberService.execute(transfer.getSourceAccount())
                .orElseThrow(() -> new BusinessException(
                        "Source account with number " + transfer.getSourceAccount() + " not found"));

        findByAccountNumberService.execute(transfer.getDestinationAccount())
                .orElseThrow(() -> new BusinessException(
                        "Destination account with number " + transfer.getDestinationAccount() + " not found"));

        if (sourceAccount.getCurrentBalance().compareTo(transfer.getAmount()) < 0) {
            throw new BusinessException(
                    "Insufficient balance. Available: " + sourceAccount.getCurrentBalance() +
                            ", Required: " + transfer.getAmount());
        }

        return transferCreateService.execute(transfer, requestingUserId);
    }
}