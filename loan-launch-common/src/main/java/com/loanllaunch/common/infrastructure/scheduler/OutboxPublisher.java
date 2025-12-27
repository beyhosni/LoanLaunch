package com.loanllaunch.common.infrastructure.scheduler;

import com.loanllaunch.common.adapter.out.persistence.OutboxRepository;
import com.loanllaunch.common.domain.outbox.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxRepository outboxRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedDelay = 2000) // Poll every 2 seconds
    @Transactional
    public void publishEvents() {
        List<OutboxEvent> events = outboxRepository.findUnpublishedEvents();

        if (!events.isEmpty()) {
            log.debug("Found {} unpublished events", events.size());

            for (OutboxEvent event : events) {
                try {
                    kafkaTemplate.send(event.getTopic(), event.getKafkaKey(), event.getPayload())
                            .whenComplete((result, ex) -> {
                                if (ex != null) {
                                    log.error("Failed to publish event: {}", event.getId(), ex);
                                    // Here we could update retry count or error message in a separate transaction
                                }
                            });

                    // Mark as published immediately (optimistic).
                    // In a real prod scenario, we might want to wait for the future or use a
                    // sophisticated retry mechanism.
                    // For MVP, we mark as published. The Kafka callback error would need handling
                    // to un-publish or alert.
                    event.setPublished(true);
                    event.setPublishedAt(Instant.now());
                    outboxRepository.save(event);

                } catch (Exception e) {
                    log.error("Error processing outbox event: {}", event.getId(), e);
                }
            }
        }
    }
}
