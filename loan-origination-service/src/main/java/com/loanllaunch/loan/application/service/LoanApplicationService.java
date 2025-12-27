package com.loanllaunch.loan.application.service;

import com.loanllaunch.common.exception.BusinessException;
import com.loanllaunch.common.exception.ResourceNotFoundException;
import com.loanllaunch.events.config.KafkaTopics;
import com.loanllaunch.events.loan.LoanApplicationCreatedEvent;
import com.loanllaunch.events.loan.LoanApplicationSubmittedEvent;
import com.loanllaunch.loan.adapter.out.persistence.LoanApplicationRepository;
import com.loanllaunch.loan.domain.model.LoanApplication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanApplicationService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public LoanApplication createLoanApplication(UUID organizationId, BigDecimal requestedAmount, 
                                                  Integer requestedTermMonths, String purpose) {
        log.info("Creating loan application for organization: {}", organizationId);

        String applicationNumber = generateApplicationNumber();
        
        LoanApplication application = LoanApplication.builder()
                .applicationNumber(applicationNumber)
                .organizationId(organizationId)
                .requestedAmount(requestedAmount)
                .requestedTermMonths(requestedTermMonths)
                .purpose(purpose)
                .status(LoanApplication.LoanStatus.DRAFT)
                .build();

        LoanApplication saved = loanApplicationRepository.save(application);

        // Publish event
        LoanApplicationCreatedEvent event = new LoanApplicationCreatedEvent(
                saved.getId(),
                new LoanApplicationCreatedEvent.LoanApplicationCreatedPayload(
                        saved.getId(),
                        saved.getApplicationNumber(),
                        saved.getOrganizationId(),
                        saved.getRequestedAmount(),
                        saved.getRequestedTermMonths(),
                        saved.getPurpose(),
                        saved.getStatus().name()
                )
        );
        kafkaTemplate.send(KafkaTopics.LOAN_EVENTS, saved.getId().toString(), event);

        log.info("Loan application created: {}", saved.getApplicationNumber());
        return saved;
    }

    @Transactional
    public LoanApplication submitLoanApplication(UUID id) {
        log.info("Submitting loan application: {}", id);

        LoanApplication application = loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LoanApplication", id.toString()));

        if (application.getStatus() != LoanApplication.LoanStatus.DRAFT) {
            throw new BusinessException("Only DRAFT applications can be submitted", "INVALID_STATUS");
        }

        application.setStatus(LoanApplication.LoanStatus.SUBMITTED);
        application.setSubmittedAt(Instant.now());
        
        LoanApplication saved = loanApplicationRepository.save(application);

        // Publish event
        LoanApplicationSubmittedEvent event = new LoanApplicationSubmittedEvent(
                saved.getId(),
                new LoanApplicationSubmittedEvent.LoanApplicationSubmittedPayload(
                        saved.getId(),
                        saved.getApplicationNumber(),
                        saved.getOrganizationId(),
                        saved.getSubmittedAt().toString()
                )
        );
        kafkaTemplate.send(KafkaTopics.LOAN_EVENTS, saved.getId().toString(), event);

        log.info("Loan application submitted: {}", saved.getApplicationNumber());
        return saved;
    }

    @Transactional(readOnly = true)
    public LoanApplication getLoanApplication(UUID id) {
        return loanApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LoanApplication", id.toString()));
    }

    @Transactional(readOnly = true)
    public List<LoanApplication> getLoanApplicationsByOrganization(UUID organizationId) {
        return loanApplicationRepository.findByOrganizationId(organizationId);
    }

    private String generateApplicationNumber() {
        String prefix = "LOAN-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
        int count = (int) (loanApplicationRepository.count() + 1);
        return String.format("%s-%05d", prefix, count);
    }
}
