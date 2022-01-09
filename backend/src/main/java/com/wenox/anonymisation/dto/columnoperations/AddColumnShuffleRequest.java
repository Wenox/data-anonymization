package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotNull;

public class AddColumnShuffleRequest extends AddOperationRequest {

  @NotNull
  private boolean withRepetitions;

  public boolean isWithRepetitions() {
    return withRepetitions;
  }

  public void setWithRepetitions(boolean withRepetitions) {
    this.withRepetitions = withRepetitions;
  }
}
