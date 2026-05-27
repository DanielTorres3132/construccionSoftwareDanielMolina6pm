package app.domain.services.loan;

import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.ports.LoanRepositoryPort;
import app.domain.services.bankaccount.BankAccountGetOrThrowService;
import app.domain.services.companyclient.CompanyClientFindByNitService;
import app.domain.models.Client.CompanyClient;
import app.domain.Exceptions.BusinessException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class LoanDisburseService {
    private final LoanRepositoryPort loanRepositoryPort;
    private final LoanGetOrThrowService getOrThrowService;
    private final BankAccountGetOrThrowService bankAccountGetOrThrowService;
    private final CompanyClientFindByNitService companyClientFindByNitService;

    public LoanDisburseService(LoanRepositoryPort loanRepositoryPort,
                              LoanGetOrThrowService getOrThrowService,
                              BankAccountGetOrThrowService bankAccountGetOrThrowService,
                              CompanyClientFindByNitService companyClientFindByNitService) {
        this.loanRepositoryPort = loanRepositoryPort;
        this.getOrThrowService = getOrThrowService;
        this.bankAccountGetOrThrowService = bankAccountGetOrThrowService;
        this.companyClientFindByNitService = companyClientFindByNitService;
    }

    public Loan execute(long loanId, String disbursementAccount) {
        Loan loan = getOrThrowService.execute(loanId);
        if (loan.getLoanStatus() != LoanStatus.APPROVED) {
            throw new IllegalStateException("Solo se pueden desembolsar préstamos en estado APPROVED.");
        }

        var account = bankAccountGetOrThrowService.execute(disbursementAccount);

        // If applicant is a company (identified by NIT), require disbursement to legal representative's account
        try {
            CompanyClient company = companyClientFindByNitService.execute(loan.getApplicantClientId());
            String legalRepId = company.getLegalRepresentativeId();
            if (legalRepId == null || !legalRepId.equals(account.getHolderId())) {
                throw new IllegalStateException("La cuenta de desembolso debe pertenecer al representante legal de la empresa.");
            }
        } catch (BusinessException ex) {
            // Not a company client: require account holder to be the loan applicant
            if (!account.getHolderId().equals(loan.getApplicantClientId())) {
                throw new IllegalStateException("La cuenta de desembolso debe pertenecer al solicitante del préstamo.");
            }
        }

        loan.setLoanStatus(LoanStatus.DISBURSED);
        loan.setDisbursementAccount(disbursementAccount);
        loan.setDisbursementDate(LocalDate.now());
        return loanRepositoryPort.save(loan);
    }
}
