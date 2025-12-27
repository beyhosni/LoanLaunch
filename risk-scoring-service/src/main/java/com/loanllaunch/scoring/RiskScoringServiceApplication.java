package com.loanllaunch.scoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.loanllaunch.scoring", "com.loanllaunch.common", "com.loanllaunch.events"})
public class RiskScoringServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RiskScoringServiceApplication.class, args);
    }
}
