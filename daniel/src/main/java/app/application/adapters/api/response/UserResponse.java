package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private long id;
    private String fullName;
    private String identificationId;
    private String email;
    private String phone;
    private String address;
    private String userName;
    private String systemRole;
    private String userStatus;
}