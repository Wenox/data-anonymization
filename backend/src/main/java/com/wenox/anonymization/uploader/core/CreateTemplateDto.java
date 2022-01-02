package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.domain.FileType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

class CreateTemplateDto {

  @NotNull
  private MultipartFile file;

  @NotNull
  private FileType type;

  @NotEmpty
  private String title;

  private String description;

  public MultipartFile getFile() {
    return file;
  }

  public void setFile(MultipartFile file) {
    this.file = file;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
