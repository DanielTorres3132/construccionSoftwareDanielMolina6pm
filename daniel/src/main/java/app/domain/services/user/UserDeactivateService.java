package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserDeactivateService {
    private final UserRepositoryPort userRepositoryPort;
    private final UserFindByIdService userFindByIdService;

    public UserDeactivateService(UserRepositoryPort userRepositoryPort, UserFindByIdService userFindByIdService) {
        this.userRepositoryPort = userRepositoryPort;
        this.userFindByIdService = userFindByIdService;
    }

    public void execute(long id) {
        User user = userFindByIdService.execute(id);
        if (user.getUserStatus() == UserStatus.INACTIVE)
            throw new BusinessException("User with ID " + id + " is already inactive");
        userRepositoryPort.updateStatus(id, UserStatus.INACTIVE);
    }
}
