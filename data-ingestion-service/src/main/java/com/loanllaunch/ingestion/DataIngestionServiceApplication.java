package com.loanllaunch.ingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.loanllaunch.ingestion", "com.loanllaunch.common", "com.loanllaunch.events"})
public class DataIngestionServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataIngestionServiceApplication.class, args);
    }
}
