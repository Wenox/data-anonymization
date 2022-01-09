package com.wenox.anonymisation.dto.columnoperations;

public class AddShorteningRequest extends AddOperationRequest {

  private Long length;

  public Long getLength() {
    return length;
  }

  public void setLength(Long length) {
    this.length = length;
  }
}
