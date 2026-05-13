package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "identification_id", unique = true, nullable = false)
    private String identificationId;

    @Column(name = "related_id")
    private long relatedId;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "system_role", nullable = false)
    private String systemRole; // SystemRole enum guardado como String

    @Column(name = "user_status", nullable = false)
    private String userStatus; // UserStatus enum guardado como String
}