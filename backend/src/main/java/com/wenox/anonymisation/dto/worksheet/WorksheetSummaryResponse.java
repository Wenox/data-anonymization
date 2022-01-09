package com.wenox.anonymisation.dto.worksheet;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.processing.domain.Outcome;
import com.wenox.uploading.template.dto.MyTemplateDto;
import java.util.List;

public class WorksheetSummaryResponse {

  private String id;
  private MyTemplateDto template;
  private List<OutcomeSummary> outcomes;

  public static WorksheetSummaryResponse from(Worksheet worksheet) {
    var dto = new WorksheetSummaryResponse();
    dto.setId(worksheet.getId());
    dto.setTemplate(MyTemplateDto.from(worksheet.getTemplate()));
    dto.setOutcomes(worksheet.getOutcomes().stream().map(OutcomeSummary::from).toList());
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

  public List<OutcomeSummary> getOutcomes() {
    return outcomes;
  }

  public void setOutcomes(List<OutcomeSummary> outcomes) {
    this.outcomes = outcomes;
  }
}
