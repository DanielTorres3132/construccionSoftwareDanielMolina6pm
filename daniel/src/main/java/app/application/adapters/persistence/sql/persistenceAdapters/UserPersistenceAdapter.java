package app.application.adapters.persistence.sql.persistenceAdapters;

import app.application.adapters.persistence.sql.entities.UserEntity;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserRepository userRepository;

    public UserPersistenceAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        UserEntity saved = userRepository.save(toEntity(user));
        return toModel(saved);
    }

    @Override
    public Optional<User> findById(long id) {
        return userRepository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<User> findByUsername(String userName) {
        return userRepository.findByUserName(userName).map(this::toModel);
    }

    @Override
    public Optional<User> findByIdentificationId(String identificationId) {
        return userRepository.findByIdentificationId(identificationId).map(this::toModel);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(long id, UserStatus status) {
        userRepository.updateStatus(id, status.name());
    }

    @Override
    public boolean existsByIdentificationId(String identificationId) {
        return userRepository.existsByIdentificationId(identificationId);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return userRepository.existsByUserName(userName);
    }

    // ── Mappers ──────────────────────────────────────────────────────────────

    private UserEntity toEntity(User user) {
        UserEntity e = new UserEntity();
        e.setFullName(user.getFullName());
        e.setIdentificationId(user.getIdentificationId());
        e.setRelatedId(user.getRelatedId());
        e.setEmail(user.getEmail());
        e.setPhone(user.getPhone());
        e.setAddress(user.getAddress());
        e.setUserName(user.getUserName());
        e.setPassword(user.getPassword());
        e.setSystemRole(user.getSystemRole() != null ? user.getSystemRole().name() : null);
        e.setUserStatus(user.getUserStatus() != null ? user.getUserStatus().name() : null);
        return e;
    }

    private User toModel(UserEntity e) {
        if (e == null) return null;
        User user = new User();
        user.setId(e.getId());
        user.setFullName(e.getFullName());
        user.setIdentificationId(e.getIdentificationId());
        user.setRelatedId(e.getRelatedId());
        user.setEmail(e.getEmail());
        user.setPhone(e.getPhone());
        user.setAddress(e.getAddress());
        user.setUserName(e.getUserName());
        user.setPassword(e.getPassword());
        user.setSystemRole(e.getSystemRole() != null ? SystemRole.valueOf(e.getSystemRole()) : null);
        user.setUserStatus(e.getUserStatus() != null ? UserStatus.valueOf(e.getUserStatus()) : null);
        return user;
    }
}