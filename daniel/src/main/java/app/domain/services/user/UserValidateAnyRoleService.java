package app.domain.services.user;

import app.domain.models.User.User;
import app.domain.models.User.enums.SystemRole;
import app.domain.models.User.enums.UserStatus;
import app.domain.Exceptions.BusinessException;

public class UserValidateAnyRoleService {
    public void execute(User user, SystemRole... allowedRoles) {
        if (user.getUserStatus() != UserStatus.ACTIVE)
            throw new BusinessException("User is not active");
        for (SystemRole role : allowedRoles) {
            if (user.getSystemRole() == role) return;
        }
        throw new BusinessException("User does not have permission to perform this action");
    }
}
