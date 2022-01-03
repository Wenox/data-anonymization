package com.wenox.anonymisation.dto;

import javax.validation.constraints.NotNull;

public class CreateWorksheetRequest {

  @NotNull
  String templateId;

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
}