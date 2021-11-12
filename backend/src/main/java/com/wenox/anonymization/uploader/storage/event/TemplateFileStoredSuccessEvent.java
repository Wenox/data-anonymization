package com.wenox.anonymization.uploader.storage.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class TemplateFileStoredSuccessEvent {

  private WorksheetTemplate worksheetTemplate;

  public TemplateFileStoredSuccessEvent(WorksheetTemplate worksheetTemplate) {
    this.worksheetTemplate = worksheetTemplate;
  }

  public WorksheetTemplate getWorksheetTemplate() {
    return worksheetTemplate;
  }

  public void setWorksheetTemplate(WorksheetTemplate worksheetTemplate) {
    this.worksheetTemplate = worksheetTemplate;
  }
}
