package com.wenox.anonymisation.dto.columnoperations;

import com.wenox.anonymisation.domain.hashing.HashingMode;
import javax.validation.constraints.NotNull;

public class AddHashingRequest extends AddOperationRequest {

  @NotNull
  private HashingMode hashingMode;

  public HashingMode getHashingMode() {
    return hashingMode;
  }

  public void setHashingMode(HashingMode hashingMode) {
    this.hashingMode = hashingMode;
  }
}
