package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AddSubstitutionRequest extends AddOperationRequest {

  @NotEmpty
  private String values;

  @NotNull
  private Boolean rememberMappings;

  public String getValues() {
    return values;
  }

  public void setValues(String values) {
    this.values = values;
  }

  public Boolean getRememberMappings() {
    return rememberMappings;
  }

  public void setRememberMappings(Boolean rememberMappings) {
    this.rememberMappings = rememberMappings;
  }
}
