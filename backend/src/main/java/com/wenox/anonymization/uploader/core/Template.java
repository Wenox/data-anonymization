package com.wenox.anonymization.uploader.core;

import com.vladmihalcea.hibernate.type.json.JsonType;
import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

@Entity
@Table(name = "templates")
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonType.class)
})
public class Template {

  @Id
  private UUID uuid = UUID.randomUUID();

  @Enumerated(EnumType.STRING)
  private TemplateStatus status;

  @Enumerated(EnumType.STRING)
  private FileType type;

  @OneToOne
  private FileEntity templateFile;

  @Type(type = "json")
  @Column(columnDefinition = "jsonb")
  private TemplateMetadata metadata;

  private String databaseName;

  private String description;

  private String author; // todo: User

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

  public TemplateMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(TemplateMetadata metadata) {
    this.metadata = metadata;
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

  public TemplateStatus getStatus() {
    return status;
  }

  public void setStatus(TemplateStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
