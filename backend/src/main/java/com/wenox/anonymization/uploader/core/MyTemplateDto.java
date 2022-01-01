package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.time.LocalDateTime;
import java.util.Optional;

public class MyTemplateDto {

  private String id;
  private String originalName;
  private TemplateStatus status;
  private FileType type;
  private String title;
  private String description;
  private LocalDateTime createdDate;
  private TemplateMetadata metadata;

  public static MyTemplateDto from(Template template) {
    var dto = new MyTemplateDto();
    dto.setId(template.getId());
    dto.setStatus(template.getStatus());
    dto.setType(template.getType());
    dto.setMetadata(template.getMetadata());
    dto.setTitle(template.getTitle());
    dto.setDescription(template.getDescription());
    dto.setCreatedDate(template.getCreatedDate());
    dto.setOriginalName(Optional.ofNullable(template.getTemplateFile())
        .map(FileEntity::getOriginalName)
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

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }

  public String getOriginalName() {
    return originalName;
  }

  public void setOriginalName(String originalName) {
    this.originalName = originalName;
  }
}
