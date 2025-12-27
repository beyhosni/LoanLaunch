package com.loanllaunch.organization.adapter.out.persistence;

import com.loanllaunch.organization.domain.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Organization entity.
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, UUID> {

    Optional<Organization> findByTaxId(String taxId);

    boolean existsByTaxId(String taxId);
}
