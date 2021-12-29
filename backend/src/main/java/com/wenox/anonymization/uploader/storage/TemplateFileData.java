package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.core.FileDto;

public class TemplateFileData extends FileData {

  private FileDto fileDTO;
  private FileType fileType;

  public FileDto getFileDTO() {
    return fileDTO;
  }

  public void setFileDTO(FileDto fileDTO) {
    this.fileDTO = fileDTO;
  }

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }
}
