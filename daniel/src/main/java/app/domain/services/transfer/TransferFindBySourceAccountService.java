package app.domain.services.transfer;

import app.domain.models.Transfer.Transfer;
import app.domain.ports.TransferRepositoryPort;
import app.domain.Exceptions.BusinessException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransferFindBySourceAccountService {
    private final TransferRepositoryPort transferRepositoryPort;

    public TransferFindBySourceAccountService(TransferRepositoryPort transferRepositoryPort) {
        this.transferRepositoryPort = transferRepositoryPort;
    }

    public List<Transfer> execute(String sourceAccount) {
        if (sourceAccount == null || sourceAccount.isBlank())
            throw new BusinessException("Source account is required");
        return transferRepositoryPort.findBySourceAccount(sourceAccount);
    }
}
