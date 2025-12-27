package com.loanllaunch.auth.domain.model;

import com.loanllaunch.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * User domain entity for authentication.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private UserRole role;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "enabled", nullable = false)
    @Builder.Default
    private Boolean enabled = true;

    public enum UserRole {
        BORROWER,
        UNDERWRITER,
        ADMIN
    }
}
