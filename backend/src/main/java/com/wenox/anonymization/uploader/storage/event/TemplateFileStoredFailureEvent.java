package com.wenox.anonymization.uploader.storage.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class TemplateFileStoredFailureEvent {

  private WorksheetTemplate template;

  public TemplateFileStoredFailureEvent(WorksheetTemplate template) {
    this.template = template;
  }

  public WorksheetTemplate getTemplate() {
    return template;
  }

  public void setTemplate(WorksheetTemplate template) {
    this.template = template;
  }
}
