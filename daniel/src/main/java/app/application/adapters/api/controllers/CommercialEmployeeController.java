package app.application.adapters.api.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import app.application.adapters.api.request.CompanyClientCreateRequest;
import app.application.adapters.api.request.NaturalPersonClientCreateRequest;
import app.application.adapters.api.response.CompanyClientResponse;
import app.application.adapters.api.response.LoanResponse;
import app.application.adapters.api.response.NaturalPersonClientResponse;
import app.application.usecases.CommercialEmployeeUseCase;
import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;

@RestController
@RequestMapping("/api/commercial-employee")
@Validated
public class CommercialEmployeeController {

    @Autowired
    private CommercialEmployeeUseCase useCase;

    public CommercialEmployeeController(CommercialEmployeeUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping("/company-client")
    public ResponseEntity<CompanyClientResponse> createCompanyClient(
            @RequestParam long requestingUserId,
            @Valid @RequestBody CompanyClientCreateRequest request) throws BusinessException {
        CompanyClient client = useCase.createCompanyClient(
                requestingUserId,
                request.getNit(),
                request.getCompanyName(),
                request.getAddress(),
                request.getPhone(),
                request.getEmail(),
                request.getLegalRepresentativeId());
        return ResponseEntity.ok(toCompanyClientResponse(client));
    }

    @PostMapping("/natural-person-client")
    public ResponseEntity<NaturalPersonClientResponse> createNaturalPersonClient(
            @RequestParam long requestingUserId,
            @Valid @RequestBody NaturalPersonClientCreateRequest request) throws BusinessException {
        NaturalPersonClient client = useCase.createNaturalPersonClient(
                requestingUserId,
                request.getIdentificationId(),
                request.getFullName(),
                request.getAddress(),
                request.getPhone(),
                request.getEmail(),
                request.getBirthDate());
        return ResponseEntity.ok(toNaturalPersonClientResponse(client));
    }

    @GetMapping("/company-client/{clientId}")
    public ResponseEntity<CompanyClientResponse> findCompanyClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        CompanyClient client = useCase.findCompanyClient(requestingUserId, clientId);
        return ResponseEntity.ok(toCompanyClientResponse(client));
    }

    @GetMapping("/natural-person-client/{clientId}")
    public ResponseEntity<NaturalPersonClientResponse> findNaturalPersonClient(
            @RequestParam long requestingUserId,
            @PathVariable long clientId) throws BusinessException {
        NaturalPersonClient client = useCase.findNaturalPersonClient(requestingUserId, clientId);
        return ResponseEntity.ok(toNaturalPersonClientResponse(client));
    }

    @GetMapping("/loan/{loanId}")
    public ResponseEntity<LoanResponse> getLoanDetail(
            @RequestParam long requestingUserId,
            @PathVariable long loanId) throws BusinessException {
        Loan loan = useCase.getLoanDetail(requestingUserId, loanId);
        return ResponseEntity.ok(toLoanResponse(loan));
    }

    // ─── Mappers ──────────────────────────────────────────────────────────────

    private static CompanyClientResponse toCompanyClientResponse(CompanyClient client) {
        CompanyClientResponse response = new CompanyClientResponse();
        response.setId(client.getId());
        response.setNit(client.getNit());
        response.setCompanyName(client.getCompanyName());
        response.setAddress(client.getFiscalAddress());
        response.setPhone(client.getCompanyPhone());
        response.setEmail(client.getCompanyEmail());
        response.setLegalRepresentativeId(client.getLegalRepresentativeId());
        return response;
    }

    private static NaturalPersonClientResponse toNaturalPersonClientResponse(NaturalPersonClient client) {
        NaturalPersonClientResponse response = new NaturalPersonClientResponse();
        response.setId(client.getId());
        response.setIdentificationId(client.getIdentificationId());
        response.setFullName(client.getFullName());
        response.setAddress(client.getAddress());
        response.setPhone(client.getPhone());
        response.setEmail(client.getEmail());
        response.setBirthDate(client.getBirthDate());
        return response;
    }

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
        response.setRejectionReason(loan.getRejectionReason());
        return response;
    }
}