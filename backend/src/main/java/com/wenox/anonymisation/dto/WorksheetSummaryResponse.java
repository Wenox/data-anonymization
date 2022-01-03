package com.wenox.anonymisation.dto;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.uploading.template.dto.MyTemplateDto;

public class WorksheetSummaryResponse {

  private MyTemplateDto template;

  public static WorksheetSummaryResponse from(Worksheet worksheet) {
    var dto = new WorksheetSummaryResponse();
    dto.setTemplate(MyTemplateDto.from(worksheet.getTemplate()));
    return dto;
  }

  public MyTemplateDto getTemplate() {
    return template;
  }

  public void setTemplate(MyTemplateDto template) {
    this.template = template;
  }
}
