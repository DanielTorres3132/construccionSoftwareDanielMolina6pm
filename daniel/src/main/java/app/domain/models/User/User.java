package app.domain.models.User;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import app.domain.models.User.enums.UserStatus;
import app.domain.models.User.enums.SystemRole;

@Getter
@Setter
@NoArgsConstructor
public class User {
    private long id;
    private String fullName;
    private String identificationId;
    private Long relatedId;
    private String email;
    private String phone;
    private String address;
    private String userName;
    private String password;
    private SystemRole systemRole;
    private UserStatus userStatus;
}