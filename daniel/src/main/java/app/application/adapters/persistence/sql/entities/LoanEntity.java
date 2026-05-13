package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loans")
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "loan_type", nullable = false)
    private String loanType; // LoanType enum guardado como String

    @Column(name = "applicant_client_id", nullable = false)
    private String applicantClientId;

    @Column(name = "requested_amount", nullable = false)
    private BigDecimal requestedAmount;

    @Column(name = "approved_amount")
    private BigDecimal approvedAmount;

    @Column(name = "interest_rate")
    private BigDecimal interestRate;

    @Column(name = "term_months")
    private int termMonths;

    @Column(name = "loan_status", nullable = false)
    private String loanStatus; // LoanStatus enum guardado como String

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "disbursement_date")
    private LocalDate disbursementDate;

    @Column(name = "disbursement_account")
    private String disbursementAccount;

    @Column(name = "approver_analyst_id")
    private long approverAnalystId;

    @Column(name = "rejection_reason")
    private String rejectionReason;
}