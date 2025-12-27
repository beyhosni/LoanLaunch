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
    
    private final com.loanllaunch.ingestion.application.service.IngestionService ingestionService;

    @PostMapping("/sync/{organizationId}")
    public ResponseEntity<ApiResponse<String>> syncBankData(@PathVariable UUID organizationId) {
        ingestionService.triggerIngestion(organizationId);
        return ResponseEntity.ok(ApiResponse.success("Bank data ingestion triggered", null));
    }
}
