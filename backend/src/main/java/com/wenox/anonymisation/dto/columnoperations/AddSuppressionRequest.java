package com.wenox.anonymisation.dto.columnoperations;

public class AddSuppressionRequest extends AddOperationRequest {

  private String suppressionToken;

  public String getSuppressionToken() {
    return suppressionToken;
  }

  public void setSuppressionToken(String suppressionToken) {
    this.suppressionToken = suppressionToken;
  }
}
