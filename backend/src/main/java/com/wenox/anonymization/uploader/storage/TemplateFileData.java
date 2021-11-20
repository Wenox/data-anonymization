package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.core.FileDTO;

public class TemplateFileData extends FileData {

  private FileDTO fileDTO;
  private FileType fileType;

  public FileDTO getFileDTO() {
    return fileDTO;
  }

  public void setFileDTO(FileDTO fileDTO) {
    this.fileDTO = fileDTO;
  }

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }
}
