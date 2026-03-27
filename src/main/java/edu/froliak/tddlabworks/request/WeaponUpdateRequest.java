package edu.froliak.tddlabworks.request;

/*
  @author eugen
  @project tdd-labworks
  @class WeaponUpdateRequest
  @version 1.0.0
  @since 3/27/2026 - 10.49
*/

public record WeaponUpdateRequest(String id, String name, String code, String description) {
}