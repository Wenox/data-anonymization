package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotNull;

public class AddRandomNumberRequest extends AddOperationRequest {

  @NotNull
  Integer minValue;

  @NotNull
  Integer maxValue;

  public Integer getMinValue() {
    return minValue;
  }

  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }
}
