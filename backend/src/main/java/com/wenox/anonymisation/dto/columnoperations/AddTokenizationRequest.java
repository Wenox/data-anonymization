package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotNull;

public class AddTokenizationRequest extends AddOperationRequest {

  @NotNull
  private Integer startingValue;

  @NotNull
  private Integer step;

  public Integer getStartingValue() {
    return startingValue;
  }

  public void setStartingValue(Integer startingValue) {
    this.startingValue = startingValue;
  }

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }
}
