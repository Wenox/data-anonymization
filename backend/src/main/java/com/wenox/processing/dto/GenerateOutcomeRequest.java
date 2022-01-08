package com.wenox.processing.dto;

import com.wenox.processing.domain.DumpMode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GenerateOutcomeRequest {

  @NotNull
  private String worksheetId;

  @NotEmpty
  private String anonymisationScriptName;

  @NotEmpty
  private String dumpName;

  @NotNull
  private DumpMode dumpMode;

  public String getWorksheetId() {
    return worksheetId;
  }

  public void setWorksheetId(String worksheetId) {
    this.worksheetId = worksheetId;
  }

  public String getAnonymisationScriptName() {
    return anonymisationScriptName;
  }

  public void setAnonymisationScriptName(String anonymisationScriptName) {
    this.anonymisationScriptName = anonymisationScriptName;
  }

  public String getDumpName() {
    return dumpName;
  }

  public void setDumpName(String dumpName) {
    this.dumpName = dumpName;
  }

  public DumpMode getDumpMode() {
    return dumpMode;
  }

  public void setDumpMode(DumpMode dumpMode) {
    this.dumpMode = dumpMode;
  }
}
