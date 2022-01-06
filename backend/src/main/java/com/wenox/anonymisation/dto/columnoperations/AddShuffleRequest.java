package com.wenox.anonymisation.dto.columnoperations;

public class AddShuffleRequest extends AddOperationRequest {

  private boolean withRepetitions;

  public boolean isWithRepetitions() {
    return withRepetitions;
  }

  public void setWithRepetitions(boolean withRepetitions) {
    this.withRepetitions = withRepetitions;
  }
}
