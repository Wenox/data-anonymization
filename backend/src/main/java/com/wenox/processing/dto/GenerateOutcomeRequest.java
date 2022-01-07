package com.wenox.processing.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class GenerateOutcomeRequest {

  @NotNull
  private final String worksheetId;

  @NotEmpty
  private final String scriptName;

  public GenerateOutcomeRequest(String worksheetId, String scriptName) {
    this.worksheetId = worksheetId;
    this.scriptName = scriptName;
  }

  public String getWorksheetId() {
    return worksheetId;
  }

  public String getScriptName() {
    return scriptName;
  }
}
