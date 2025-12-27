package com.loanllaunch.events.loan;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Event published when a loan application is submitted.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanApplicationSubmittedEvent extends BaseEvent {

    private LoanApplicationSubmittedPayload payload;

    public LoanApplicationSubmittedEvent(UUID loanApplicationId, LoanApplicationSubmittedPayload payload) {
        super(loanApplicationId, "LoanApplication");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanApplicationSubmittedPayload {
        private UUID loanApplicationId;
        private String applicationNumber;
        private UUID organizationId;
        private String submittedAt;
    }
}
