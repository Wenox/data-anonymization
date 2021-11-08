package com.wenox.anonymization.uploader.restorer.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class DatabaseRestoreSuccessEvent {

  private final WorksheetTemplate worksheetTemplate;

  public DatabaseRestoreSuccessEvent(final WorksheetTemplate worksheetTemplate) {
    this.worksheetTemplate = worksheetTemplate;
  }

  public WorksheetTemplate getWorksheetTemplate() {
    return worksheetTemplate;
  }
}
