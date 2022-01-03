package com.wenox.anonymisation.dto;

import com.wenox.anonymisation.domain.Worksheet;

public class WorksheetCreatedResponse {

  private String id;
  private String templateId;
  private String userId;

  public static WorksheetCreatedResponse from(Worksheet worksheet) {
    var dto = new WorksheetCreatedResponse();
    dto.setId(worksheet.getId());
    dto.setTemplateId(worksheet.getTemplate().getId());
    dto.setUserId(worksheet.getUser().getId());
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getTemplateId() {
    return templateId;
  }

  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
}
