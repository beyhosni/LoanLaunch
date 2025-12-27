package com.loanllaunch.organization.adapter.out.persistence;

import com.loanllaunch.organization.domain.model.OrganizationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for OrganizationUser entity.
 */
@Repository
public interface OrganizationUserRepository extends JpaRepository<OrganizationUser, UUID> {

    List<OrganizationUser> findByOrganizationId(UUID organizationId);

    List<OrganizationUser> findByUserId(UUID userId);

    Optional<OrganizationUser> findByOrganizationIdAndUserId(UUID organizationId, UUID userId);

    boolean existsByOrganizationIdAndUserId(UUID organizationId, UUID userId);
}
