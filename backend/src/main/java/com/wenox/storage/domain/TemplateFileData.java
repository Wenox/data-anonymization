package com.wenox.storage.domain;

import com.wenox.users.domain.FileType;

public class TemplateFileData extends FileData {

  private FileType fileType;

  public TemplateFileData(FileData fileData, FileType fileType) {
    super(fileData);
    this.fileType = fileType;
  }

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }
}
