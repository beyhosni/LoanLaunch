package com.loanllaunch.organization.adapter.in.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for updating an organization.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrganizationRequest {

    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @Size(max = 100, message = "Industry must not exceed 100 characters")
    private String industry;

    private LocalDate foundedDate;

    private Integer employeeCount;

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

    @Size(max = 50, message = "Phone must not exceed 50 characters")
    private String phone;

    @Email(message = "Invalid email format")
    private String email;

    @Size(max = 255, message = "Website must not exceed 255 characters")
    private String website;
}
