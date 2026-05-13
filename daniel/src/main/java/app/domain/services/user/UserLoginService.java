package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {
    private final UserRepositoryPort userRepositoryPort;

    public UserLoginService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User execute(String userName, String password) {
        User user = userRepositoryPort.findByUsername(userName)
                .orElseThrow(() -> new BusinessException("Invalid username or password"));
        if (!user.getPassword().equals(password))
            throw new BusinessException("Invalid username or password");
        if (user.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException("User account is blocked. Please contact support");
        if (user.getUserStatus() == UserStatus.INACTIVE)
            throw new BusinessException("User account is inactive. Please contact support");
        return user;
    }
}
