package app.application.adapters.api.controllers;

import app.application.usecases.InternalAnalystUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.Log.RegisterLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/internal-analyst")
@Validated
public class InternalAnalystController {
    private final InternalAnalystUseCase useCase;

    public InternalAnalystController(InternalAnalystUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/company-client/{clientId}")
    public ResponseEntity<CompanyClient> findCompanyClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        CompanyClient client = useCase.findCompanyClient(requestingUserId, clientId);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/natural-person-client/{clientId}")
    public ResponseEntity<NaturalPersonClient> findNaturalPersonClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        NaturalPersonClient client = useCase.findNaturalPersonClient(requestingUserId, clientId);
        return ResponseEntity.ok(client);
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<Loan> getLoanDetail(
            @RequestParam long requestingUserId,
            @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getLoanDetail(requestingUserId, loanId);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/loan/{loanId}/approve")
    public ResponseEntity<Loan> approveLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @RequestParam BigDecimal approvedAmount,
            @RequestParam BigDecimal interestRate) throws BusinessException {
        Loan loan = useCase.approveLoan(requestingUserId, loanId, approvedAmount, interestRate);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/loan/{loanId}/reject")
    public ResponseEntity<Loan> rejectLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @RequestParam String reason) throws BusinessException {
        Loan loan = useCase.rejectLoan(requestingUserId, loanId, reason);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/audit/user/{userId}")
    public ResponseEntity<List<RegisterLog>> getAuditLogByUser(
            @RequestParam long requestingUserId,
            @PathVariable long userId) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByUser(requestingUserId, userId);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/audit/operation-type/{operationType}")
    public ResponseEntity<List<RegisterLog>> getAuditLogByOperationType(
            @RequestParam long requestingUserId,
            @PathVariable String operationType) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByOperationType(requestingUserId, operationType);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/audit/product/{productId}")
    public ResponseEntity<List<RegisterLog>> getAuditLogByProduct(
            @RequestParam long requestingUserId,
            @PathVariable long productId) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByProduct(requestingUserId, productId);
        return ResponseEntity.ok(logs);
    }
}
