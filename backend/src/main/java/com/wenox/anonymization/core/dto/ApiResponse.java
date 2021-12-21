package com.wenox.anonymization.core.dto;

public class ApiResponse {
  private String message = "Action completed successfully";
  private Boolean success = true;

  public ApiResponse() {

  }

  public static ApiResponse ofSuccess(String message) {
    return new ApiResponse(message, true);
  }

  public static ApiResponse ofError(String message) {
    return new ApiResponse(message, false);
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
