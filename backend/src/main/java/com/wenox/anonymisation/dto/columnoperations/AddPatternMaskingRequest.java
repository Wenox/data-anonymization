package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotNull;

public class AddPatternMaskingRequest extends AddOperationRequest {

  @NotNull
  private String pattern;

  @NotNull
  private boolean discardExcessiveCharacters;

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public boolean isDiscardExcessiveCharacters() {
    return discardExcessiveCharacters;
  }

  public void setDiscardExcessiveCharacters(boolean discardExcessiveCharacters) {
    this.discardExcessiveCharacters = discardExcessiveCharacters;
  }
}
