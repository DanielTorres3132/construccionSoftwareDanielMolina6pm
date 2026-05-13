package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.ports.UserRepositoryPort;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserFindAllService {
    private final UserRepositoryPort userRepositoryPort;

    public UserFindAllService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public List<User> execute() {
        return userRepositoryPort.findAll();
    }
}
