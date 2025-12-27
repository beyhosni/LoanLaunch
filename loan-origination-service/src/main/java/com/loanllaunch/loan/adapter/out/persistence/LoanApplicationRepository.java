package com.loanllaunch.loan.adapter.out.persistence;

import com.loanllaunch.loan.domain.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, UUID> {
    Optional<LoanApplication> findByApplicationNumber(String applicationNumber);
    List<LoanApplication> findByOrganizationId(UUID organizationId);
    boolean existsByApplicationNumber(String applicationNumber);
}
