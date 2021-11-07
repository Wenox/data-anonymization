package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.FileEntity;

public class FileUploadedSuccessEvent {

  private final FileEntity file;

  public FileUploadedSuccessEvent(FileEntity file) {
    this.file = file;
  }

  public FileEntity getFile() {
    return file;
  }
}
