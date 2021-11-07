package com.wenox.anonymization.uploader.restorer.event;

public class DatabaseRestoreFailureEvent {
  private final Exception exception;

  public DatabaseRestoreFailureEvent(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }
}