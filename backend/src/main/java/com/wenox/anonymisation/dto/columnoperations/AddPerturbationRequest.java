package com.wenox.anonymisation.dto.columnoperations;

import com.wenox.anonymisation.domain.perturbation.PerturbationMode;
import javax.validation.constraints.NotNull;

public class AddPerturbationRequest extends AddOperationRequest {

  @NotNull
  Integer value;

  @NotNull
  PerturbationMode perturbationMode;

  Integer minValue;

  Integer maxValue;

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public PerturbationMode getPerturbationMode() {
    return perturbationMode;
  }

  public void setPerturbationMode(PerturbationMode perturbationMode) {
    this.perturbationMode = perturbationMode;
  }

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
