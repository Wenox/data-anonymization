package com.wenox.processing.dto;

import com.wenox.processing.domain.DumpMode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GenerateOutcomeRequest {

  @NotNull
  private final String worksheetId;

  @NotEmpty
  private final String anonymisationScriptName;

  @NotNull
  private final DumpMode dumpMode;

  public GenerateOutcomeRequest(String worksheetId, String anonymisationScriptName, DumpMode dumpMode) {
    this.worksheetId = worksheetId;
    this.anonymisationScriptName = anonymisationScriptName;
    this.dumpMode = dumpMode;
  }

  public String getWorksheetId() {
    return worksheetId;
  }

  public String getAnonymisationScriptName() {
    return anonymisationScriptName;
  }

  public DumpMode getDumpMode() {
    return dumpMode;
  }
}
