package com.loanllaunch.organization.adapter.out.kafka;

import com.loanllaunch.events.config.KafkaTopics;
import com.loanllaunch.events.organization.OrganizationCreatedEvent;
import com.loanllaunch.events.organization.OrganizationUpdatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Kafka event publisher for organization events.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OrganizationEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrganizationCreated(OrganizationCreatedEvent event) {
        log.info("Publishing OrganizationCreatedEvent for organization: {}", event.getAggregateId());
        kafkaTemplate.send(KafkaTopics.ORGANIZATION_EVENTS, event.getAggregateId().toString(), event);
    }

    public void publishOrganizationUpdated(OrganizationUpdatedEvent event) {
        log.info("Publishing OrganizationUpdatedEvent for organization: {}", event.getAggregateId());
        kafkaTemplate.send(KafkaTopics.ORGANIZATION_EVENTS, event.getAggregateId().toString(), event);
    }
}
