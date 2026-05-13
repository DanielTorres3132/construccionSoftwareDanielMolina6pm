package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserBlockService {
    private final UserRepositoryPort userRepositoryPort;
    private final UserFindByIdService userFindByIdService;

    public UserBlockService(UserRepositoryPort userRepositoryPort, UserFindByIdService userFindByIdService) {
        this.userRepositoryPort = userRepositoryPort;
        this.userFindByIdService = userFindByIdService;
    }

    public void execute(long id) {
        User user = userFindByIdService.execute(id);
        if (user.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException("User with ID " + id + " is already blocked");
        userRepositoryPort.updateStatus(id, UserStatus.BLOCKED);
    }
}
