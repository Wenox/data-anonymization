package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.FileDTO;
import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class WorksheetTemplateCreatedEvent {

  private final WorksheetTemplate worksheetTemplate;
  private final FileDTO fileDTO;

  public WorksheetTemplateCreatedEvent(WorksheetTemplate worksheetTemplate, FileDTO fileDTO) {
    this.worksheetTemplate = worksheetTemplate;
    this.fileDTO = fileDTO;
  }

  public WorksheetTemplate getWorksheetTemplate() {
    return worksheetTemplate;
  }

  public FileDTO getFileDTO() {
    return fileDTO;
  }
}
