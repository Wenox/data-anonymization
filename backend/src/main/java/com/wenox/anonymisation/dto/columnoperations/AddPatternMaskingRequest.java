package com.wenox.anonymisation.dto.columnoperations;

import javax.validation.constraints.NotNull;

public class AddPatternMaskingRequest extends AddOperationRequest {

  @NotNull
  private String pattern;

  @NotNull
  private Character maskingCharacter;

  @NotNull
  private boolean discardExcessiveCharacters;

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public Character getMaskingCharacter() {
    return maskingCharacter;
  }

  public void setMaskingCharacter(Character maskingCharacter) {
    this.maskingCharacter = maskingCharacter;
  }

  public boolean isDiscardExcessiveCharacters() {
    return discardExcessiveCharacters;
  }

  public void setDiscardExcessiveCharacters(boolean discardExcessiveCharacters) {
    this.discardExcessiveCharacters = discardExcessiveCharacters;
  }
}
