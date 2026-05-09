package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.Client.CompanyClient;
import app.domain.models.Client.NaturalPersonClient;
import app.domain.models.Loan.Loan;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;

import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserValidateRoleService;
import app.domain.services.companyclient.CompanyClientCreateService;
import app.domain.services.companyclient.CompanyClientFindByIdService;
import app.domain.services.naturalpersonclient.NaturalPersonClientCreateService;
import app.domain.services.naturalpersonclient.NaturalPersonClientFindByIdService;
import app.domain.services.loan.LoanGetOrThrowService;

import java.time.LocalDate;

@Service
public class CommercialEmployeeUseCase {

    @Autowired
    private UserFindByIdService userFindByIdService;
    @Autowired
    private UserValidateRoleService userValidateRoleService;
    @Autowired
    private CompanyClientCreateService companyClientCreateService;
    @Autowired
    private CompanyClientFindByIdService companyClientFindByIdService;
    @Autowired
    private NaturalPersonClientCreateService naturalPersonClientCreateService;
    @Autowired
    private NaturalPersonClientFindByIdService naturalPersonClientFindByIdService;
    @Autowired
    private LoanGetOrThrowService loanGetOrThrowService;

    public CommercialEmployeeUseCase(UserFindByIdService userFindByIdService,
                                     UserValidateRoleService userValidateRoleService,
                                     CompanyClientCreateService companyClientCreateService,
                                     CompanyClientFindByIdService companyClientFindByIdService,
                                     NaturalPersonClientCreateService naturalPersonClientCreateService,
                                     NaturalPersonClientFindByIdService naturalPersonClientFindByIdService,
                                     LoanGetOrThrowService loanGetOrThrowService) {
        this.userFindByIdService = userFindByIdService;
        this.userValidateRoleService = userValidateRoleService;
        this.companyClientCreateService = companyClientCreateService;
        this.companyClientFindByIdService = companyClientFindByIdService;
        this.naturalPersonClientCreateService = naturalPersonClientCreateService;
        this.naturalPersonClientFindByIdService = naturalPersonClientFindByIdService;
        this.loanGetOrThrowService = loanGetOrThrowService;
    }

    public CompanyClient createCompanyClient(long requestingUserId, String nit, String companyName,
                                             String address, String phone, String email,
                                             String legalRepresentativeId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        CompanyClient client = new CompanyClient();
        client.setNit(nit);
        client.setCompanyName(companyName);
        client.setFiscalAddress(address);
        client.setCompanyPhone(phone);
        client.setCompanyEmail(email);
        client.setLegalRepresentativeId(legalRepresentativeId);
        return companyClientCreateService.execute(client);
    }

    public NaturalPersonClient createNaturalPersonClient(long requestingUserId, String identificationId,
                                                         String fullName, String address, String phone,
                                                         String email, LocalDate birthDate) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        NaturalPersonClient client = new NaturalPersonClient();
        client.setIdentificationId(identificationId);
        client.setFullName(fullName);
        client.setAddress(address);
        client.setPhone(phone);
        client.setEmail(email);
        client.setBirthDate(birthDate);
        return naturalPersonClientCreateService.execute(client);
    }

    public CompanyClient findCompanyClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return companyClientFindByIdService.execute(clientId);
    }

    public NaturalPersonClient findNaturalPersonClient(long requestingUserId, long clientId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return naturalPersonClientFindByIdService.execute(clientId);
    }

    public Loan getLoanDetail(long requestingUserId, long loanId) throws BusinessException {
        User user = userFindByIdService.execute(requestingUserId);
        userValidateRoleService.execute(user, SystemRole.COMMERCIAL_EMPLOYEE);
        return loanGetOrThrowService.execute(loanId);
    }
}