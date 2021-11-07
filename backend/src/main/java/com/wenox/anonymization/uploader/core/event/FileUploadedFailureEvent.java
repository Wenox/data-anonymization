package com.wenox.anonymization.uploader.core.event;

import java.io.IOException;

public class FileUploadedFailureEvent {

  private final IOException exception;

  public FileUploadedFailureEvent(final IOException exception) {
    this.exception = exception;
  }

  public IOException getException() {
    return exception;
  }
}
