package com.loanllaunch.events.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

/**
 * Base class for all domain events.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent {

    private String eventId;
    private String eventType;
    private UUID aggregateId;
    private String aggregateType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant timestamp;

    private String version;
    private EventMetadata metadata;

    public BaseEvent(UUID aggregateId, String aggregateType) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = this.getClass().getSimpleName();
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.timestamp = Instant.now();
        this.version = "1.0";
        this.metadata = new EventMetadata();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventMetadata {
        private UUID userId;
        private UUID correlationId;
        private UUID causationId;
    }
}
