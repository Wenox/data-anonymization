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

  @Enumerated(EnumType.STRING)
  private DumpMode dumpMode;

  private String dumpName;

  private String mirrorDatabaseName;

  private String anonymisationScriptName;

  @OneToOne
  private FileEntity anonymisationFile;

  @OneToOne
  private FileEntity dumpFile;

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

  public DumpMode getDumpMode() {
    return dumpMode;
  }

  public void setDumpMode(DumpMode dumpMode) {
    this.dumpMode = dumpMode;
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

  public String getAnonymisationScriptName() {
    return anonymisationScriptName;
  }

  public FileEntity getAnonymisationFile() {
    return anonymisationFile;
  }

  public void setAnonymisationFile(FileEntity fileEntity) {
    this.anonymisationFile = fileEntity;
  }

  public void setAnonymisationScriptName(String scriptName) {
    this.anonymisationScriptName = scriptName;
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

  public String getDumpName() {
    return dumpName;
  }

  public FileEntity getDumpFile() {
    return dumpFile;
  }

  public void setDumpFile(FileEntity dumpFile) {
    this.dumpFile = dumpFile;
  }

  public void setDumpName(String dumpName) {
    this.dumpName = dumpName;
  }

  public void setProcessingEndDate(LocalDateTime processingEndDate) {
    this.processingEndDate = processingEndDate;
  }
}
