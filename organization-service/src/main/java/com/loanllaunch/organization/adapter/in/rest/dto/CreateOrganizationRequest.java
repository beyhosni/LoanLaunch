package com.loanllaunch.organization.adapter.in.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for creating an organization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrganizationRequest {

    @NotBlank(message = "Organization name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Legal name is required")
    @Size(max = 255, message = "Legal name must not exceed 255 characters")
    private String legalName;

    @NotBlank(message = "Tax ID is required")
    @Size(max = 50, message = "Tax ID must not exceed 50 characters")
    private String taxId;

    @Size(max = 100, message = "Industry must not exceed 100 characters")
    private String industry;

    @Past(message = "Founded date must be in the past")
    private LocalDate foundedDate;

    @Min(value = 1, message = "Employee count must be at least 1")
    private Integer employeeCount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Annual revenue must be positive")
    private BigDecimal annualRevenue;

    @Size(max = 255, message = "Address line 1 must not exceed 255 characters")
    private String addressLine1;

    @Size(max = 255, message = "Address line 2 must not exceed 255 characters")
    private String addressLine2;

    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @Size(max = 50, message = "State must not exceed 50 characters")
    private String state;

    @Size(max = 20, message = "Postal code must not exceed 20 characters")
    private String postalCode;

    @Size(max = 50, message = "Country must not exceed 50 characters")
    private String country;

    @Pattern(regexp = "^[+]?[0-9\\s\\-()]+$", message = "Invalid phone number format")
    @Size(max = 50, message = "Phone must not exceed 50 characters")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;
}
