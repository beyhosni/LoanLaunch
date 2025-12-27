package com.loanllaunch.events.organization;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Event published when a new organization is created.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrganizationCreatedEvent extends BaseEvent {

    private OrganizationCreatedPayload payload;

    public OrganizationCreatedEvent(UUID organizationId, OrganizationCreatedPayload payload) {
        super(organizationId, "Organization");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrganizationCreatedPayload {
        private UUID organizationId;
        private String name;
        private String legalName;
        private String taxId;
        private String industry;
        private String status;
    }
}
