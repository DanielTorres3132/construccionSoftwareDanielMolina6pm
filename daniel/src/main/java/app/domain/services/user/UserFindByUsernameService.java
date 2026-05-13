package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserFindByUsernameService {
    private final UserRepositoryPort userRepositoryPort;

    public UserFindByUsernameService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User execute(String userName) {
        return userRepositoryPort.findByUsername(userName)
                .orElseThrow(() -> new BusinessException("User with username " + userName + " not found"));
    }
}
