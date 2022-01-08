package com.wenox.processing.dto;

import com.wenox.processing.domain.DumpMode;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import java.time.ZoneOffset;

public class OutcomeResponse {

  private String id;
  private String worksheetId;
  private String templateName;
  private OutcomeStatus outcomeStatus;
  private DumpMode dumpMode;
  private String dumpName;
  private String anonymisationScriptName;
  private Long processingTime;

  public static OutcomeResponse from(Outcome outcome) {
    var dto = new OutcomeResponse();
    dto.setId(outcome.getId());
    dto.setWorksheetId(outcome.getWorksheet().getId());
    dto.setTemplateName(outcome.getWorksheet().getTemplate().getTitle());
    dto.setOutcomeStatus(outcome.getOutcomeStatus());
    dto.setDumpMode(outcome.getDumpMode());
    dto.setDumpName(outcome.getDumpName());
    dto.setAnonymisationScriptName(outcome.getAnonymisationScriptName());
    dto.setProcessingTime(outcome.getProcessingEndDate().toEpochSecond(ZoneOffset.UTC) - outcome.getProcessingStartDate().toEpochSecond(ZoneOffset.UTC));
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getWorksheetId() {
    return worksheetId;
  }

  public void setWorksheetId(String worksheetId) {
    this.worksheetId = worksheetId;
  }

  public String getTemplateName() {
    return templateName;
  }

  public void setTemplateName(String templateName) {
    this.templateName = templateName;
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
