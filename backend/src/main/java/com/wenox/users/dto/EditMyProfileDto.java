package com.wenox.users.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EditMyProfileDto {

  @NotBlank
  @Size(min = 2, max = 40)
  private String firstName;

  @NotBlank
  @Size(min = 2, max = 40)
  private String lastName;

  @NotBlank
  @Size(min = 2, max = 1000)
  private String purpose;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPurpose() {
    return purpose;
  }

  public void setPurpose(String purpose) {
    this.purpose = purpose;
  }
}
