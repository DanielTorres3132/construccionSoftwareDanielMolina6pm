package app.application.adapters.api.controllers;

import app.application.adapters.api.request.LoanApprovalRequest;
import app.application.adapters.api.request.LoanRejectionRequest;
import app.application.adapters.api.response.LoanResponse;
import app.application.adapters.api.response.AuditLogResponse;
import app.application.usecases.InternalAnalystUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.Log.RegisterLog;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public ResponseEntity<LoanResponse> getLoanDetail(
            @RequestParam long requestingUserId,
            @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getLoanDetail(requestingUserId, loanId);
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @PostMapping("/loan/{loanId}/approve")
    public ResponseEntity<LoanResponse> approveLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @Valid @RequestBody LoanApprovalRequest request) throws BusinessException {
        Loan loan = useCase.approveLoan(requestingUserId, loanId, request.getApprovedAmount(), request.getInterestRate());
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @PostMapping("/loan/{loanId}/reject")
    public ResponseEntity<LoanResponse> rejectLoan(
            @RequestParam long requestingUserId,
            @PathVariable long loanId,
            @Valid @RequestBody LoanRejectionRequest request) throws BusinessException {
        Loan loan = useCase.rejectLoan(requestingUserId, loanId, request.getReason());
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    @GetMapping("/audit/user/{userId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogByUser(
            @RequestParam long requestingUserId,
            @PathVariable long userId) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByUser(requestingUserId, userId);
        return ResponseEntity.ok(logs.stream().map(this::toAuditLogResponse).toList());
    }

    @GetMapping("/audit/operation-type/{operationType}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogByOperationType(
            @RequestParam long requestingUserId,
            @PathVariable String operationType) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByOperationType(requestingUserId, operationType);
        return ResponseEntity.ok(logs.stream().map(this::toAuditLogResponse).toList());
    }

    @GetMapping("/audit/product/{productId}")
    public ResponseEntity<List<AuditLogResponse>> getAuditLogByProduct(
            @RequestParam long requestingUserId,
            @PathVariable long productId) throws BusinessException {
        List<RegisterLog> logs = useCase.getAuditLogByProduct(requestingUserId, productId);
        return ResponseEntity.ok(logs.stream().map(this::toAuditLogResponse).toList());
    }

    private LoanResponse toLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setClientId(loan.getClientId());
        response.setLoanType(loan.getLoanType().toString());
        response.setRequestedAmount(loan.getRequestedAmount());
        response.setApprovedAmount(loan.getApprovedAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTermMonths(loan.getTermMonths());
        response.setStatus(loan.getStatus().toString());
        response.setCreatedAt(loan.getCreatedAt());
        response.setApprovedAt(loan.getApprovedAt());
        response.setRejectionReason(loan.getRejectionReason());
        return response;
    }

    private AuditLogResponse toAuditLogResponse(RegisterLog log) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(log.getId());
        response.setUserId(log.getUserId());
        response.setOperationType(log.getOperationType());
        response.setDescription(log.getDescription());
        response.setProductId(log.getProductId());
        response.setProductType(log.getProductType());
        response.setTimestamp(log.getTimestamp());
        response.setDetails(log.getDetails());
        response.setStatus(log.getStatus());
        return response;
    }
}
