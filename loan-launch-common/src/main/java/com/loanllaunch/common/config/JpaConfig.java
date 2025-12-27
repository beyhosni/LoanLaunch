package com.loanllaunch.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA configuration for auditing support.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}
