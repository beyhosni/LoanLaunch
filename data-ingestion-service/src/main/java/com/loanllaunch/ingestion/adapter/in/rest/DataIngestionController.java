package com.loanllaunch.ingestion.adapter.in.rest;

import com.loanllaunch.common.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/ingestion")
@RequiredArgsConstructor
public class DataIngestionController {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping("/sync/{organizationId}")
    public ResponseEntity<ApiResponse<String>> syncBankData(@PathVariable UUID organizationId) {
        log.info("Simulating bank data sync for organization: {}", organizationId);
        // Simulate data ingestion and publish event
        return ResponseEntity.ok(ApiResponse.success("Bank data sync initiated", "Data ingestion started"));
    }
}
