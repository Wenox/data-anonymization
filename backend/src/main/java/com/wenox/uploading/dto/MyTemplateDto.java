package com.wenox.uploading.dto;

import com.wenox.storage.domain.FileEntity;
import com.wenox.uploading.domain.template.RestoreMode;
import com.wenox.uploading.domain.template.Template;
import com.wenox.uploading.domain.template.TemplateStatus;
import com.wenox.users.domain.FileType;
import com.wenox.uploading.domain.metadata.TemplateMetadata;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class MyTemplateDto {

  private String id;
  private String originalFileName;
  private TemplateStatus status;
  private FileType type;
  private RestoreMode restoreMode;
  private String title;
  private String description;
  private String createdDate;
  private TemplateMetadata metadata;

  public static MyTemplateDto from(Template template) {
    var dto = new MyTemplateDto();
    dto.setId(template.getId());
    dto.setStatus(template.getStatus());
    dto.setType(template.getType());
    dto.setRestoreMode(template.getRestoreMode());
    dto.setMetadata(template.getMetadata());
    dto.setTitle(template.getTitle());
    dto.setDescription(template.getDescription());
    dto.setCreatedDate(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(template.getCreatedDate()));
    dto.setOriginalFileName(Optional.ofNullable(template.getTemplateFile())
        .map(FileEntity::getOriginalFileName)
        .orElse("")
    );
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public TemplateStatus getStatus() {
    return status;
  }

  public void setStatus(TemplateStatus status) {
    this.status = status;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public TemplateMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(TemplateMetadata metadata) {
    this.metadata = metadata;
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

  public String getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(String createdDate) {
    this.createdDate = createdDate;
  }

  public String getOriginalFileName() {
    return originalFileName;
  }

  public RestoreMode getRestoreMode() {
    return restoreMode;
  }

  public void setRestoreMode(RestoreMode restoreMode) {
    this.restoreMode = restoreMode;
  }

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }
}
