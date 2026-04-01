package app.domain.services;

import app.domain.models.User.User;
import app.domain.models.User.enums.UserStatus;
import app.domain.models.User.enums.SystemRole;
import app.domain.ports.UserRepositoryPort;
import app.domain.Exceptions.BusinessException;

import java.util.List;

public class UserService {

    private final UserRepositoryPort userRepositoryPort;

    public UserService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    // ─── Crear usuario ────────────────────────────────────────────────────────

    public User createUser(User user) {
        validateRequiredFields(user);

        if (userRepositoryPort.existsByIdentificationId(user.getIdentificationId()))
            throw new BusinessException("A user with identification ID " + user.getIdentificationId() + " already exists");

        if (userRepositoryPort.existsByUsername(user.getUserName()))
            throw new BusinessException("Username " + user.getUserName() + " is already taken");

        user.setUserStatus(UserStatus.ACTIVE);
        return userRepositoryPort.save(user);
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public User findById(long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(() -> new BusinessException("User with ID " + id + " not found"));
    }

    public User findByUsername(String userName) {
        return userRepositoryPort.findByUsername(userName)
                .orElseThrow(() -> new BusinessException("User with username " + userName + " not found"));
    }

    public User findByIdentificationId(String identificationId) {
        return userRepositoryPort.findByIdentificationId(identificationId)
                .orElseThrow(() -> new BusinessException("User with identification ID " + identificationId + " not found"));
    }

    public List<User> findAll() {
        return userRepositoryPort.findAll();
    }

    // ─── Cambio de estado ─────────────────────────────────────────────────────

    public void blockUser(long id) {
        User user = findById(id);

        if (user.getUserStatus() == UserStatus.BLOCKED)
            throw new BusinessException("User with ID " + id + " is already blocked");

        userRepositoryPort.updateStatus(id, UserStatus.BLOCKED);
    }

    public void deactivateUser(long id) {
        User user = findById(id);

        if (user.getUserStatus() == UserStatus.INACTIVE)
            throw new BusinessException("User with ID " + id + " is already inactive");

        userRepositoryPort.updateStatus(id, UserStatus.INACTIVE);
    }

    public void activateUser(long id) {
        User user = findById(id);

        if (user.getUserStatus() == UserStatus.ACTIVE)
            throw new BusinessException("User with ID " + id + " is already active");

        userRepositoryPort.updateStatus(id, UserStatus.ACTIVE);
    }

    // ─── Login ────────────────────────────────────────────────────────────────

    public User login(String userName, String password) {
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

    // ─── Validación de permisos ───────────────────────────────────────────────

    public void validateRole(User user, SystemRole requiredRole) {
        if (user.getUserStatus() != UserStatus.ACTIVE)
            throw new BusinessException("User is not active");

        if (user.getSystemRole() != requiredRole)
            throw new BusinessException("User does not have permission to perform this action");
    }

    public void validateAnyRole(User user, SystemRole... allowedRoles) {
        if (user.getUserStatus() != UserStatus.ACTIVE)
            throw new BusinessException("User is not active");

        for (SystemRole role : allowedRoles) {
            if (user.getSystemRole() == role) return;
        }

        throw new BusinessException("User does not have permission to perform this action");
    }

    // ─── Validaciones internas ────────────────────────────────────────────────

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
