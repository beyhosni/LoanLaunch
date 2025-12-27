package com.loanllaunch.loan.domain.model;

import com.loanllaunch.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "loan_applications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplication extends BaseEntity {

    @Column(name = "application_number", unique = true, nullable = false, length = 50)
    private String applicationNumber;

    @Column(name = "organization_id", nullable = false)
    private UUID organizationId;

    @Column(name = "requested_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal requestedAmount;

    @Column(name = "requested_term_months", nullable = false)
    private Integer requestedTermMonths;

    @Column(name = "purpose", columnDefinition = "TEXT", nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    @Builder.Default
    private LoanStatus status = LoanStatus.DRAFT;

    @Column(name = "decision_id")
    private UUID decisionId;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    public enum LoanStatus {
        DRAFT,
        SUBMITTED,
        UNDER_REVIEW,
        APPROVED,
        REJECTED,
        DISBURSED
    }
}
