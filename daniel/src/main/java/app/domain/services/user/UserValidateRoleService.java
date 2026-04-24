package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.models.User.enums.UserStatus;
import app.domain.Exceptions.BusinessException;

public class UserValidateRoleService {
    public void execute(User user, SystemRole requiredRole) {
        if (user.getUserStatus() != UserStatus.ACTIVE)
            throw new BusinessException("User is not active");
        if (user.getSystemRole() != requiredRole)
            throw new BusinessException("User does not have permission to perform this action");
    }
}
