package com.loanllaunch.events.ingestion;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BankDataIngestedEvent extends BaseEvent {

    private BankDataIngestedPayload payload;

    public BankDataIngestedEvent(UUID organizationId, BankDataIngestedPayload payload) {
        super(organizationId, "Organization");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BankDataIngestedPayload {
        private UUID organizationId;
        private String provider;
        private int transactionCount;
        private LocalDate periodStart;
        private LocalDate periodEnd;
        private String dataLocation;
    }
}
