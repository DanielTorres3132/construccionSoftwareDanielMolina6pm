package app.application.adapters.persistence.sql.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "company_clients")
public class CompanyClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "nit", unique = true, nullable = false)
    private String nit;

    @Column(name = "company_email")
    private String companyEmail;

    @Column(name = "company_phone")
    private String companyPhone;

    @Column(name = "fiscal_address")
    private String fiscalAddress;

    @Column(name = "legal_representative_id")
    private String legalRepresentativeId;
}