package app.application.adapters.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserAuthResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String role;
    private String document;
}
