package com.loanllaunch.events.config;

/**
 * Kafka topic names constants.
 */
public final class KafkaTopics {

    private KafkaTopics() {
        // Utility class
    }

    // Organization Events
    public static final String ORGANIZATION_EVENTS = "organization-events";

    // User Events
    public static final String USER_EVENTS = "user-events";

    // Data Ingestion Events
    public static final String DATA_INGESTION_EVENTS = "data-ingestion-events";

    // Normalization Events
    public static final String NORMALIZATION_EVENTS = "normalization-events";

    // Risk Scoring Events
    public static final String RISK_SCORING_EVENTS = "risk-scoring-events";

    // Decision Events
    public static final String DECISION_EVENTS = "decision-events";

    // Loan Events
    public static final String LOAN_EVENTS = "loan-events";
}
