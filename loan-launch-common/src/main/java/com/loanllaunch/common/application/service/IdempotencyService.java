package com.loanllaunch.common.application.service;

import com.loanllaunch.common.adapter.out.persistence.ProcessedEventRepository;
import com.loanllaunch.common.domain.event.ProcessedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private final ProcessedEventRepository processedEventRepository;

    @Transactional
    public boolean processIfNew(String eventId, String consumerGroup, Runnable processor) {
        if (processedEventRepository.existsByEventIdAndConsumerGroup(eventId, consumerGroup)) {
            log.info("Event {} already processed by group {}. Skipping.", eventId, consumerGroup);
            return false;
        }

        processor.run();

        processedEventRepository.save(ProcessedEvent.builder()
                .eventId(eventId)
                .consumerGroup(consumerGroup)
                .processedAt(Instant.now())
                .build());
        return true;
    }
}
