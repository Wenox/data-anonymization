package com.wenox.uploading.template.dto;

import com.wenox.uploading.template.domain.RestoreMode;
import com.wenox.users.domain.FileType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class CreateTemplateDto {

  @NotNull
  private MultipartFile file;

  @NotNull
  private FileType type;

  @NotNull
  private RestoreMode restoreMode;

  @NotEmpty
  private String title;

  private String description;

  public MultipartFile getFile() {
    return file;
  }

  public RestoreMode getRestoreMode() {
    return restoreMode;
  }

  public void setRestoreMode(RestoreMode restoreMode) {
    this.restoreMode = restoreMode;
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
