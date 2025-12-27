package com.loanllaunch.events.loan;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Event published when a loan application is created.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanApplicationCreatedEvent extends BaseEvent {

    private LoanApplicationCreatedPayload payload;

    public LoanApplicationCreatedEvent(UUID loanApplicationId, LoanApplicationCreatedPayload payload) {
        super(loanApplicationId, "LoanApplication");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanApplicationCreatedPayload {
        private UUID loanApplicationId;
        private String applicationNumber;
        private UUID organizationId;
        private BigDecimal requestedAmount;
        private Integer requestedTermMonths;
        private String purpose;
        private String status;
    }
}
