package com.wenox.anonymization.core.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EditMyProfileDto {

  @Email
  @NotBlank
  @Size(max = 40)
  private String email;

  @NotBlank
  @Size(min = 2, max = 40)
  private String firstName;

  @NotBlank
  @Size(min = 2, max = 40)
  private String lastName;

  @NotBlank
  @Size(min = 2, max = 1000)
  private String purpose;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

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
