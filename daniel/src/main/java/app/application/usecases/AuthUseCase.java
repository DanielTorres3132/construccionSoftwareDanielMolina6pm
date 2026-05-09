package app.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.domain.Exceptions.BusinessException;
import app.domain.models.User.User;
import app.domain.services.user.UserFindByIdService;
import app.domain.services.user.UserFindByIdentificationIdService;
import app.domain.services.user.UserLoginService;

@Service
public class AuthUseCase {

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserFindByIdentificationIdService userFindByIdentificationIdService;
    @Autowired
    private UserFindByIdService userFindByIdService;

    public AuthUseCase(UserLoginService userLoginService,
                       UserFindByIdentificationIdService userFindByIdentificationIdService,
                       UserFindByIdService userFindByIdService) {
        this.userLoginService = userLoginService;
        this.userFindByIdentificationIdService = userFindByIdentificationIdService;
        this.userFindByIdService = userFindByIdService;
    }

    public User authenticate(String userName, String password) throws BusinessException {
        return userLoginService.execute(userName, password);
    }

    public User findByIdentificationId(String identificationId) throws BusinessException {
        return userFindByIdentificationIdService.execute(identificationId);
    }

    public User findById(long userId) throws BusinessException {
        return userFindByIdService.execute(userId);
    }
}