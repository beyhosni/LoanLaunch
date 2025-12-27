package com.loanllaunch.loan;

import com.loanllaunch.common.adapter.out.persistence.OutboxRepository;
import com.loanllaunch.common.domain.outbox.OutboxEvent;
import com.loanllaunch.events.config.KafkaTopics;
import com.loanllaunch.events.loan.LoanApplicationCreatedEvent;
import com.loanllaunch.loan.application.service.LoanApplicationService;
import com.loanllaunch.loan.domain.model.LoanApplication;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
@TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=validate",
    "spring.flyway.enabled=true"
})
public class LoanApplicationOutboxIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Container
    @ServiceConnection
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.0"));

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private OutboxRepository outboxRepository;

    private KafkaMessageListenerContainer<String, LoanApplicationCreatedEvent> container;
    private BlockingQueue<ConsumerRecord<String, LoanApplicationCreatedEvent>> records;

    @BeforeEach
    public void setUp() {
        records = new LinkedBlockingQueue<>();
        ContainerProperties containerProps = new ContainerProperties(KafkaTopics.LOAN_EVENTS);
        
        DefaultKafkaConsumerFactory<String, LoanApplicationCreatedEvent> cf = new DefaultKafkaConsumerFactory<>(
            getConsumerProps(),
            new StringDeserializer(),
            new JsonDeserializer<>(LoanApplicationCreatedEvent.class, false)
        );

        container = new KafkaMessageListenerContainer<>(cf, containerProps);
        container.setupMessageListener((MessageListener<String, LoanApplicationCreatedEvent>) records::add);
        container.start();
        await().until(() -> container.isRunning());
    }

    @AfterEach
    public void tearDown() {
        container.stop();
    }

    @Test
    void shouldCreateLoanAndPublishEventViaOutbox() throws InterruptedException {
        // Given
        UUID organizationId = UUID.randomUUID();
        
        // When: We create a loan application
        LoanApplication loan = loanApplicationService.createLoanApplication(
            organizationId, 
            new BigDecimal("50000"), 
            24, 
            "Expansion"
        );

        // Then 1: It should be saved in DB
        assertThat(loan.getId()).isNotNull();

        // Then 2: It should be in the Outbox table
        List<OutboxEvent> outboxEvents = outboxRepository.findAll();
        assertThat(outboxEvents).hasSize(1);
        OutboxEvent outboxEvent = outboxEvents.get(0);
        assertThat(outboxEvent.getAggregateId()).isEqualTo(loan.getId().toString());
        assertThat(outboxEvent.getType()).isEqualTo("LoanApplicationCreatedEvent");

        // Then 3: The mock OutboxPublisher (scheduled in app) should pick it up and publish to Kafka
        // We wait for the scheduled task to run (2s interval defined in OutboxPublisher)
        await().atMost(5, TimeUnit.SECONDS).until(() -> {
            OutboxEvent updatedEvent = outboxRepository.findById(outboxEvent.getId()).orElseThrow();
            return updatedEvent.isPublished();
        });

        // Then 4: The Kafka Consumer should receive the event
        ConsumerRecord<String, LoanApplicationCreatedEvent> received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received).isNotNull();
        assertThat(received.value().getAggregateId()).isEqualTo(loan.getId());
        assertThat(received.value().getPayload().getRequestedAmount()).isEqualByComparingTo("50000");
    }

    private java.util.Map<String, Object> getConsumerProps() {
        return java.util.Map.of(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafka.getBootstrapServers(),
            ConsumerConfig.GROUP_ID_CONFIG, "test-group",
            ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"
        );
    }
}
