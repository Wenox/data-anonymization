package com.wenox.anonymisation.dto.worksheet;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.uploading.template.dto.MyTemplateDto;

public class WorksheetSummaryResponse {

  private String id;
  private MyTemplateDto template;

  public static WorksheetSummaryResponse from(Worksheet worksheet) {
    var dto = new WorksheetSummaryResponse();
    dto.setId(worksheet.getId());
    dto.setTemplate(MyTemplateDto.from(worksheet.getTemplate()));
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public MyTemplateDto getTemplate() {
    return template;
  }

  public void setTemplate(MyTemplateDto template) {
    this.template = template;
  }
}
