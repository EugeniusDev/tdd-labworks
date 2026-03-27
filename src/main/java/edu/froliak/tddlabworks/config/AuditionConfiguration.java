package edu.froliak.tddlabworks.config;

/*
  @author eugen
  @project tdd-labworks
  @class AuditionConfiguration
  @version 1.0.0
  @since 3/27/2026 - 10.52
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@Configuration
public class AuditionConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}