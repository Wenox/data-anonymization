package com.wenox.processing.domain;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.users.domain.FileType;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "outcomes")
public class Outcome {

  @Id
  private final String id = UUID.randomUUID().toString();

  @OneToOne
  private Worksheet worksheet;

  @Enumerated(EnumType.STRING)
  private OutcomeStatus outcomeStatus;

  private String mirrorDatabaseName;

  private String scriptName;

  @OneToOne
  private FileEntity fileEntity;

  private LocalDateTime processingStartDate;

  private LocalDateTime processingEndDate;

  public String getTemplateDatabaseName() {
    return worksheet.getTemplate().getTemplateDatabaseName();
  }

  public FileType getTemplateType() {
    return worksheet.getTemplate().getType();
  }

  public String getId() {
    return id;
  }

  public Worksheet getWorksheet() {
    return worksheet;
  }

  public void setWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
  }

  public OutcomeStatus getOutcomeStatus() {
    return outcomeStatus;
  }

  public String getMirrorDatabaseName() {
    return mirrorDatabaseName;
  }

  public String getScriptName() {
    return scriptName;
  }

  public FileEntity getFileEntity() {
    return fileEntity;
  }

  public void setFileEntity(FileEntity fileEntity) {
    this.fileEntity = fileEntity;
  }

  public void setScriptName(String scriptName) {
    this.scriptName = scriptName;
  }

  public void setMirrorDatabaseName(String mirrorDatabaseName) {
    this.mirrorDatabaseName = mirrorDatabaseName;
  }

  public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
    this.outcomeStatus = outcomeStatus;
  }

  public LocalDateTime getProcessingStartDate() {
    return processingStartDate;
  }

  public void setProcessingStartDate(LocalDateTime processingStartDate) {
    this.processingStartDate = processingStartDate;
  }

  public LocalDateTime getProcessingEndDate() {
    return processingEndDate;
  }

  public void setProcessingEndDate(LocalDateTime processingEndDate) {
    this.processingEndDate = processingEndDate;
  }
}
