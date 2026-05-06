package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String identificationId;
    private String identificationType;
    private String phone;
    private String address;
    private String status;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
}
