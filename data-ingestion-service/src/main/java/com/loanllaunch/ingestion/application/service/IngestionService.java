package com.loanllaunch.ingestion.application.service;

import com.loanllaunch.common.application.service.OutboxService;
import com.loanllaunch.events.config.KafkaTopics;
import com.loanllaunch.events.ingestion.BankDataIngestedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestionService {

    private final OutboxService outboxService;

    @Transactional
    public void triggerIngestion(UUID organizationId) {
        log.info("Triggering data ingestion for organization: {}", organizationId);

        // Simulation of fetching data from external provider (Plaid/Codat)
        // ... (e.g. calling external API, saving raw data to DB/S3)
        
        int simulatedTransactionCount = 150 + (int)(Math.random() * 50);
        
        log.info("Ingestion completed for org {}. Found {} transactions.", organizationId, simulatedTransactionCount);

        // Publish event via Outbox
        BankDataIngestedEvent event = new BankDataIngestedEvent(
                organizationId,
                new BankDataIngestedEvent.BankDataIngestedPayload(
                        organizationId,
                        "SIMULATED_PROVIDER",
                        simulatedTransactionCount,
                        LocalDate.now().minusMonths(6),
                        LocalDate.now(),
                        "s3://loanlaunch/raw/" + organizationId + "/transactions.json"
                )
        );
        
        outboxService.saveEvent(event, "loanlaunch.ingestion.bankdata.v1", organizationId.toString());
        log.info("BankDataIngestedEvent saved to outbox");
    }
}
