package com.wenox.anonymization.core.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RemoveMyAccountDto {

  @NotBlank
  @Size(min = 4, max = 40)
  private String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
