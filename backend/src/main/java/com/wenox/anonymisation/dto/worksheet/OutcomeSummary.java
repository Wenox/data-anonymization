package com.wenox.anonymisation.dto.worksheet;

import com.wenox.processing.domain.DumpMode;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import java.time.ZoneOffset;

public class OutcomeSummary {

  private String id;
  private OutcomeStatus outcomeStatus;
  private DumpMode dumpMode;
  private String dumpName;
  private String anonymisationScriptName;
  private Long processingTime;

  public static OutcomeSummary from(Outcome outcome) {
    var dto = new OutcomeSummary();
    dto.setId(outcome.getId());
    dto.setOutcomeStatus(outcome.getOutcomeStatus());
    dto.setDumpName(outcome.getDumpName());
    dto.setDumpMode(outcome.getDumpMode());
    dto.setAnonymisationScriptName(outcome.getAnonymisationScriptName());
    if (outcome.getProcessingEndDate() == null) {
      dto.setProcessingTime(-1L);
    } else {
      dto.setProcessingTime(outcome.getProcessingEndDate().toEpochSecond(ZoneOffset.UTC) - outcome.getProcessingStartDate().toEpochSecond(ZoneOffset.UTC));
    }
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public OutcomeStatus getOutcomeStatus() {
    return outcomeStatus;
  }

  public void setOutcomeStatus(OutcomeStatus outcomeStatus) {
    this.outcomeStatus = outcomeStatus;
  }

  public DumpMode getDumpMode() {
    return dumpMode;
  }

  public void setDumpMode(DumpMode dumpMode) {
    this.dumpMode = dumpMode;
  }

  public String getDumpName() {
    return dumpName;
  }

  public void setDumpName(String dumpName) {
    this.dumpName = dumpName;
  }

  public String getAnonymisationScriptName() {
    return anonymisationScriptName;
  }

  public void setAnonymisationScriptName(String anonymisationScriptName) {
    this.anonymisationScriptName = anonymisationScriptName;
  }

  public Long getProcessingTime() {
    return processingTime;
  }

  public void setProcessingTime(Long processingTime) {
    this.processingTime = processingTime;
  }
}
