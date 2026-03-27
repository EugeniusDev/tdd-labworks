package edu.froliak.tddlabworks.response;

/*
  @author eugen
  @project tdd-labworks
  @class BaseMetaData
  @version 1.0.0
  @since 3/27/2026 - 10.59
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMetaData {
    @Builder.Default
    private int code = 200;
    @Builder.Default
    private boolean success = true;
    @Builder.Default
    private String errorMessage = null;

    public BaseMetaData(int code, boolean success) {
        this.code = code;
        this.success = success;
    }
}