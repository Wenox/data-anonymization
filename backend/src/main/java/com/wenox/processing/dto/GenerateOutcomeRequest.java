package com.wenox.processing.dto;

import com.wenox.processing.domain.DumpMode;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GenerateOutcomeRequest {

  @NotNull
  private final String worksheetId;

  @NotEmpty
  private final String scriptName;

  @NotNull
  private final DumpMode dumpMode;

  public GenerateOutcomeRequest(String worksheetId, String scriptName, DumpMode dumpMode) {
    this.worksheetId = worksheetId;
    this.scriptName = scriptName;
    this.dumpMode = dumpMode;
  }

  public String getWorksheetId() {
    return worksheetId;
  }

  public String getScriptName() {
    return scriptName;
  }

  public DumpMode getDumpMode() {
    return dumpMode;
  }
}
