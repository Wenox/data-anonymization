package com.wenox.anonymisation.dto.columnoperations;


public class AddGeneralisationRequest extends AddOperationRequest {

  private Integer minValue;

  private Integer maxValue;

  private Integer intervalSize;

  private Integer numberOfDistributions;

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

  public Integer getIntervalSize() {
    return intervalSize;
  }

  public void setIntervalSize(Integer intervalSize) {
    this.intervalSize = intervalSize;
  }

  public Integer getNumberOfDistributions() {
    return numberOfDistributions;
  }

  public void setNumberOfDistributions(Integer numberOfDistributions) {
    this.numberOfDistributions = numberOfDistributions;
  }
}
