package app.application.adapters.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthResponse {
    private long id;
    private String userName;
    private String email;
    private String fullName;
    private String systemRole;
    private String identificationId;
}