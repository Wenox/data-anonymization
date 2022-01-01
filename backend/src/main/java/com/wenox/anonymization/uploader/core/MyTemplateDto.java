package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;

public class MyTemplateDto {

  private String id;
  private String originalFileName;
  private TemplateStatus status;
  private FileType type;
  private String title;
  private String description;
  private String createdDate;
  private TemplateMetadata metadata;

  public static MyTemplateDto from(Template template) {
    var dto = new MyTemplateDto();
    dto.setId(template.getId());
    dto.setStatus(template.getStatus());
    dto.setType(template.getType());
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

  public void setOriginalFileName(String originalFileName) {
    this.originalFileName = originalFileName;
  }
}
