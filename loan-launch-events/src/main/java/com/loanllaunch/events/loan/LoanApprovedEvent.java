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
 * Event published when a loan is approved.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class LoanApprovedEvent extends BaseEvent {

    private LoanApprovedPayload payload;

    public LoanApprovedEvent(UUID loanApplicationId, LoanApprovedPayload payload) {
        super(loanApplicationId, "LoanApplication");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoanApprovedPayload {
        private UUID loanApplicationId;
        private UUID organizationId;
        private BigDecimal approvedAmount;
        private BigDecimal interestRate;
        private Integer termMonths;
        private BigDecimal monthlyPayment;
        private BigDecimal totalRepayment;
    }
}
