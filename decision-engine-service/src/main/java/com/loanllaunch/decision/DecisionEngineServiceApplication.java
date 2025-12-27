package com.loanllaunch.decision;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.loanllaunch.decision", "com.loanllaunch.common", "com.loanllaunch.events"})
public class DecisionEngineServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DecisionEngineServiceApplication.class, args);
    }
}
