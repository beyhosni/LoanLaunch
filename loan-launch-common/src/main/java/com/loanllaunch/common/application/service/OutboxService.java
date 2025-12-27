package com.loanllaunch.common.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loanllaunch.common.adapter.out.persistence.OutboxRepository;
import com.loanllaunch.common.domain.outbox.OutboxEvent;
import com.loanllaunch.events.base.BaseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveEvent(BaseEvent event, String topic, String key) {
        try {
            String payload = objectMapper.writeValueAsString(event);

            OutboxEvent outboxEvent = OutboxEvent.builder()
                    .id(UUID.randomUUID())
                    .aggregateType(event.getAggregateType())
                    .aggregateId(event.getAggregateId().toString())
                    .type(event.getEventType())
                    .payload(payload)
                    .topic(topic)
                    .kafkaKey(key)
                    .createdAt(Instant.now())
                    .published(false)
                    .build();

            outboxRepository.save(outboxEvent);
            log.debug("Event saved to outbox: {} - {}", event.getEventType(), event.getEventId());
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize event: {}", event.getEventId(), e);
            throw new RuntimeException("Failed to serialize event", e);
        }
    }
}
