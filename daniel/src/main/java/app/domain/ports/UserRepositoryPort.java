package app.domain.ports;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(long id);
    Optional<User> findByUsername(String userName);
    Optional<User> findByIdentificationId(String identificationId);
    List<User> findAll();
    void updateStatus(long id, UserStatus status);
    boolean existsByIdentificationId(String identificationId);
    boolean existsByUsername(String userName);
}
