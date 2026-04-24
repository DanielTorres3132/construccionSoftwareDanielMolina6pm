package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class UserFindByIdService {
    private final UserRepositoryPort userRepositoryPort;

    public UserFindByIdService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User execute(long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("User with ID " + id + " not found"));
    }
}
