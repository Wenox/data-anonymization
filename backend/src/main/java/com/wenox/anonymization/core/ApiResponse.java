package com.wenox.anonymization.core;

public class ApiResponse {
  private String message = "Action completed successfully";
  private Boolean success = true;

  public ApiResponse() {

  }

  public ApiResponse(String message) {
    this.message = message;
  }

  public ApiResponse(String message, Boolean success) {
    this.message = message;
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }
}
