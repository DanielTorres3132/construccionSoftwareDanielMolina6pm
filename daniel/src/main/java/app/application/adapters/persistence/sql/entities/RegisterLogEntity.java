package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "register_logs")
public class RegisterLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(name = "operation_date_time", nullable = false)
    private LocalDateTime operationDateTime;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "user_role", nullable = false)
    private String userRole; // SystemRole enum guardado como String

    @Column(name = "affected_product_id")
    private String affectedProductId;

    @Column(name = "detail_data", columnDefinition = "TEXT")
    private String detailData; // Map<String,Object> serializado como JSON String
}