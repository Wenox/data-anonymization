package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddShorteningRequest extends AddOperationRequest {

  @NotNull
  @Min(1)
  private int length;

  @NotNull
  private boolean endsWithPeriod;

  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public boolean isEndsWithPeriod() {
    return endsWithPeriod;
  }

  public void setEndsWithPeriod(boolean endsWithPeriod) {
    this.endsWithPeriod = endsWithPeriod;
  }
}
