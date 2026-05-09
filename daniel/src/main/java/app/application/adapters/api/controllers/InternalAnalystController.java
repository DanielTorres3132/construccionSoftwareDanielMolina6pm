package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.LoanApprovalRequest;
import app.application.adapters.api.request.LoanRejectionRequest;
import app.application.adapters.api.response.AuditLogResponse;
import app.application.adapters.api.response.LoanResponse;
import app.application.usecases.InternalAnalystUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.Log.RegisterLog;

import java.util.List;

@RestController
@RequestMapping("/api/internal-analyst")
@Validated
public class InternalAnalystController {

    @Autowired
    private InternalAnalystUseCase useCase;

    public InternalAnalystController(InternalAnalystUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/company-client/{clientId}")
    public ResponseEntity<CompanyClient> findCompanyClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        return ResponseEntity.ok(useCase.findCompanyClient(requestingUserId, clientId));
    }

    @GetMapping("/natural-person-client/{clientId}")
    public ResponseEntity<NaturalPersonClient> findNaturalPersonClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        return ResponseEntity.ok(useCase.findNaturalPersonClient(requestingUserId, clientId));
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

    // ─── Mappers ──────────────────────────────────────────────────────────────

    private static LoanResponse toLoanResponse(Loan loan) {
        LoanResponse response = new LoanResponse();
        response.setId(loan.getId());
        response.setApplicantClientId(loan.getApplicantClientId());
        response.setLoanType(loan.getLoanType().toString());
        response.setRequestedAmount(loan.getRequestedAmount());
        response.setApprovedAmount(loan.getApprovedAmount());
        response.setInterestRate(loan.getInterestRate());
        response.setTermMonths(loan.getTermMonths());
        response.setLoanStatus(loan.getLoanStatus().toString());
        response.setRequestDate(loan.getRequestDate());
        response.setApprovalDate(loan.getApprovalDate());
        response.setDisbursementDate(loan.getDisbursementDate());
        response.setDisbursementAccount(loan.getDisbursementAccount());
        response.setRejectionReason(loan.getRejectionReason());
        return response;
    }

    private static AuditLogResponse toAuditLogResponse(RegisterLog log) {
        AuditLogResponse response = new AuditLogResponse();
        response.setId(log.getId());
        response.setOperationType(log.getOperationType());
        response.setOperationDateTime(log.getOperationDateTime());
        response.setUserId(log.getUserId());
        response.setUserRole(log.getUserRole().toString());
        response.setAffectedProductId(log.getAffectedProductId());
        response.setDetailData(log.getDetailData());
        return response;
    }
}