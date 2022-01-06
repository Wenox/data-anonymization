package com.wenox.processing.domain;

import com.wenox.anonymisation.domain.Worksheet;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Entity;
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

  private Boolean processingSuccss;

  private LocalDateTime processingStartDate;

  private LocalDateTime processingEndDate;

  public String getId() {
    return id;
  }

  public Worksheet getWorksheet() {
    return worksheet;
  }

  public void setWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
  }

  public Boolean getProcessingSuccss() {
    return processingSuccss;
  }

  public void setProcessingSuccss(Boolean processingSuccss) {
    this.processingSuccss = processingSuccss;
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
