package app.domain.models.Loan;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import app.domain.models.Loan.enums.LoanStatus;
import app.domain.models.Loan.enums.LoanType;

@Setter
@Getter
@NoArgsConstructor
public class Loan { //prestamo
    private long id; 
    private LoanType loanType; //tipo de prestamo
    private String applicantClientId; //id del cliente solicitante
    private BigDecimal requestedAmount; //monto solicitado
    private BigDecimal approvedAmount; //monto aprobado
    private BigDecimal interestRate; //tasa de interes
    private int termMonths; //plaso en meses
    private LoanStatus loanStatus; //estado del prestamo
    private LocalDate requestDate; //fecha de solicitud
    private LocalDate approvalDate; //fecha de aprobacion
    private LocalDate disbursementDate; //fecha de desembolso
    private String disbursementAccount; //cuenta de desembolso
    private long approverAnalystId; //id del analista que aprueba o rechaza
    private String rejectionReason; //razon de rechazo
}