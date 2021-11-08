package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class WorksheetTemplateCreatedEvent {

  private final WorksheetTemplate worksheetTemplate;

  public WorksheetTemplateCreatedEvent(WorksheetTemplate worksheetTemplate) {
    this.worksheetTemplate = worksheetTemplate;
  }

  public WorksheetTemplate getWorksheetTemplate() {
    return worksheetTemplate;
  }
}
