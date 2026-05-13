package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UserFindByIdentificationIdService {
    private final UserRepositoryPort userRepositoryPort;

    public UserFindByIdentificationIdService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User execute(String identificationId) {
        return userRepositoryPort.findByIdentificationId(identificationId)
                .orElseThrow(() -> new BusinessException("User with identification ID " + identificationId + " not found"));
    }
}
