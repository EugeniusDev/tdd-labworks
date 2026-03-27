package edu.froliak.tddlabworks.request;

/*
  @author eugen
  @project tdd-labworks
  @class ItemUpdateRequest
  @version 1.0.0
  @since 3/27/2026 - 10.49
*/

public record ItemUpdateRequest(String id,String name, String code, String description) {
}