package com.loanllaunch.loan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {
        "com.loanllaunch.loan",
        "com.loanllaunch.common",
        "com.loanllaunch.events"
})
@EnableJpaAuditing
public class LoanOriginationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoanOriginationServiceApplication.class, args);
    }
}
