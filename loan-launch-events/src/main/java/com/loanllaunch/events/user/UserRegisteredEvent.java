package com.loanllaunch.events.user;

import com.loanllaunch.events.base.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Event published when a new user is registered.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserRegisteredEvent extends BaseEvent {

    private UserRegisteredPayload payload;

    public UserRegisteredEvent(UUID userId, UserRegisteredPayload payload) {
        super(userId, "User");
        this.payload = payload;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserRegisteredPayload {
        private UUID userId;
        private String email;
        private String role;
        private UUID organizationId;
    }
}
