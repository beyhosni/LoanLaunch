package com.loanllaunch.events.organization;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Event published when an organization is updated.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrganizationUpdatedEvent extends BaseEvent {

    private OrganizationUpdatedPayload payload;

    public OrganizationUpdatedEvent(UUID organizationId, OrganizationUpdatedPayload payload) {
        super(organizationId, "Organization");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrganizationUpdatedPayload {
        private UUID organizationId;
        private String name;
        private String status;
    }
}
