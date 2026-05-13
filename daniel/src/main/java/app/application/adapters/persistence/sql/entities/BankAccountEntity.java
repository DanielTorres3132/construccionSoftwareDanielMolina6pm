package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "bank_accounts")
public class BankAccountEntity {

    @Id
    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType; // AccountType enum guardado como String

    @Column(name = "holder_id", nullable = false)
    private String holderId;

    @Column(name = "current_balance", nullable = false)
    private BigDecimal currentBalance;

    @Column(name = "currency", nullable = false)
    private String currency; // Currency enum guardado como String

    @Column(name = "account_status", nullable = false)
    private String accountStatus; // AccountStatus enum guardado como String

    @Column(name = "opening_date", nullable = false)
    private LocalDate openingDate;
}