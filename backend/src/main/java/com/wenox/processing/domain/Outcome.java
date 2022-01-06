package com.wenox.processing.domain;

import com.wenox.anonymisation.domain.Worksheet;
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

  private LocalDateTime processingStartDate;

  private LocalDateTime processingEndDate;

  public String getTemplateDatabaseName() {
    return worksheet.getTemplate().getTemplateDatabaseName();
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
