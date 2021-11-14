package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.commons.domain.FileType;
import org.springframework.web.multipart.MultipartFile;

public class TemplateFileData extends FileData {
  private MultipartFile multipartFile;
  private FileType fileType;

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }

  public void setMultipartFile(MultipartFile multipartFile) {
    this.multipartFile = multipartFile;
  }

  public FileType getFileType() {
    return fileType;
  }

  public void setFileType(FileType fileType) {
    this.fileType = fileType;
  }
}
