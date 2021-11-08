package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "worksheet_templates")
public class WorksheetTemplate {

  @Id
  private UUID uuid = UUID.randomUUID();

  private FileType type;

  @OneToOne
  private FileEntity templateFile;

  @OneToOne
  private FileEntity metadataFile;

  private String databaseName;

  private String description;

  private String author; // todo: User

  private String status; // todo: WorksheetTemplateStatus

  private LocalDateTime createdDate;

  public UUID getUuid() {
    return uuid;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public FileEntity getTemplateFile() {
    return templateFile;
  }

  public void setTemplateFile(FileEntity templateFile) {
    this.templateFile = templateFile;
  }

  public FileEntity getMetadataFile() {
    return metadataFile;
  }

  public void setMetadataFile(FileEntity metadataFile) {
    this.metadataFile = metadataFile;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
