package edu.froliak.tddlabworks.config;

/*
  @author eugen
  @project tdd-labworks
  @class AuditorAwareImpl
  @version 1.0.0
  @since 3/27/2026 - 10.52
*/

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        //return Optional.of("admin");
        return Optional.of(System.getProperty("user.name"));
    }
}