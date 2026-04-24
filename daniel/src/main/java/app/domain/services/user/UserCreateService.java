package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;

public class UserCreateService {
    private final UserRepositoryPort userRepositoryPort;

    public UserCreateService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User execute(User user) {
        validateRequiredFields(user);
        if (userRepositoryPort.existsByIdentificationId(user.getIdentificationId()))
            throw new BusinessException("A user with identification ID " + user.getIdentificationId() + " already exists");
        if (userRepositoryPort.existsByUsername(user.getUserName()))
            throw new BusinessException("Username " + user.getUserName() + " is already taken");
        user.setUserStatus(UserStatus.ACTIVE);
        return userRepositoryPort.save(user);
    }

    private void validateRequiredFields(User user) {
        if (user.getFullName() == null || user.getFullName().isBlank())
            throw new BusinessException("Full name is required");
        if (user.getIdentificationId() == null || user.getIdentificationId().isBlank())
            throw new BusinessException("Identification ID is required");
        if (user.getEmail() == null || !user.getEmail().contains("@") || !user.getEmail().contains("."))
            throw new BusinessException("Email is invalid");
        if (user.getPhone() == null || user.getPhone().length() < 7 || user.getPhone().length() > 15)
            throw new BusinessException("Phone must be between 7 and 15 digits");
        if (user.getAddress() == null || user.getAddress().isBlank())
            throw new BusinessException("Address is required");
        if (user.getUserName() == null || user.getUserName().isBlank())
            throw new BusinessException("Username is required");
        if (user.getPassword() == null || user.getPassword().isBlank())
            throw new BusinessException("Password is required");
        if (user.getSystemRole() == null)
            throw new BusinessException("System role is required");
    }
}
