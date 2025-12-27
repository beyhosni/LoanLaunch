package com.loanllaunch.organization.domain.model;

import com.loanllaunch.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * OrganizationUser domain entity representing users within an organization.
 */
@Entity
@Table(name = "organization_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"organization_id", "user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationUser extends BaseEntity {

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private UserRole role;

    public enum UserRole {
        OWNER,
        ADMIN,
        MEMBER
    }
}
