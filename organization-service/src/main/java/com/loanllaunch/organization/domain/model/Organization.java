package com.loanllaunch.organization.domain.model;

import com.loanllaunch.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Organization domain entity representing a small business.
 */
@Entity
@Table(name = "organizations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Organization extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Column(name = "tax_id", unique = true, nullable = false, length = 50)
    private String taxId;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "founded_date")
    private LocalDate foundedDate;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "annual_revenue", precision = 15, scale = 2)
    private BigDecimal annualRevenue;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city", length = 100)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "country", length = 50)
    private String country;

    @Column(name = "phone", length = 50)
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private OrganizationStatus status = OrganizationStatus.PENDING;

    public enum OrganizationStatus {
        PENDING,
        VERIFIED,
        REJECTED
    }
}
