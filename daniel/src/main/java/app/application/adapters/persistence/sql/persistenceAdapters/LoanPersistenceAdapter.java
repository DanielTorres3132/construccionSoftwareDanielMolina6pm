package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.LoanEntity;
import app.application.adapters.persistence.sql.repositories.LoanRepository;
import app.domain.models.Loan.Loan;
import app.domain.models.Loan.enums.LoanStatus;
import app.domain.models.Loan.enums.LoanType;
import app.domain.ports.LoanRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanPersistenceAdapter implements LoanRepositoryPort {

    private final LoanRepository loanRepository;

    public LoanPersistenceAdapter(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public Loan save(Loan loan) {
        LoanEntity saved = loanRepository.save(toEntity(loan));
        return toModel(saved);
    }

    @Override
    public Optional<Loan> findById(long id) {
        return loanRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<Loan> findByApplicantClientId(String applicantClientId) {
        return loanRepository.findByApplicantClientId(applicantClientId).map(this::toModel);
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private LoanEntity toEntity(Loan loan) {
        LoanEntity e = new LoanEntity();
        e.setId(loan.getId());
        e.setLoanType(loan.getLoanType() != null ? loan.getLoanType().name() : null);
        e.setApplicantClientId(loan.getApplicantClientId());
        e.setRequestedAmount(loan.getRequestedAmount());
        e.setApprovedAmount(loan.getApprovedAmount());
        e.setInterestRate(loan.getInterestRate());
        e.setTermMonths(loan.getTermMonths());
        e.setLoanStatus(loan.getLoanStatus() != null ? loan.getLoanStatus().name() : null);
        e.setRequestDate(loan.getRequestDate());
        e.setApprovalDate(loan.getApprovalDate());
        e.setDisbursementDate(loan.getDisbursementDate());
        e.setDisbursementAccount(loan.getDisbursementAccount());
        e.setApproverAnalystId(loan.getApproverAnalystId());
        e.setRejectionReason(loan.getRejectionReason());
        return e;
    }

    private Loan toModel(LoanEntity e) {
        if (e == null) return null;
        Loan loan = new Loan();
        loan.setId(e.getId());
        loan.setLoanType(e.getLoanType() != null ? LoanType.valueOf(e.getLoanType()) : null);
        loan.setApplicantClientId(e.getApplicantClientId());
        loan.setRequestedAmount(e.getRequestedAmount());
        loan.setApprovedAmount(e.getApprovedAmount());
        loan.setInterestRate(e.getInterestRate());
        loan.setTermMonths(e.getTermMonths());
        loan.setLoanStatus(e.getLoanStatus() != null ? LoanStatus.valueOf(e.getLoanStatus()) : null);
        loan.setRequestDate(e.getRequestDate());
        loan.setApprovalDate(e.getApprovalDate());
        loan.setDisbursementDate(e.getDisbursementDate());
        loan.setDisbursementAccount(e.getDisbursementAccount());
        loan.setApproverAnalystId(e.getApproverAnalystId());
        loan.setRejectionReason(e.getRejectionReason());
        return loan;
    }
}