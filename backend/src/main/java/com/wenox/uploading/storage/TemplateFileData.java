package com.wenox.uploading.storage;

import com.wenox.storage.domain.FileData;
import com.wenox.users.domain.FileType;
import com.wenox.uploading.template.dto.FileDto;

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
