package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;
import org.springframework.web.multipart.MultipartFile;

public class WorksheetTemplateCreatedEvent {

  private final WorksheetTemplate worksheetTemplate;
  private final MultipartFile multipartFile;

  public WorksheetTemplateCreatedEvent(WorksheetTemplate worksheetTemplate, MultipartFile multipartFile) {
    this.worksheetTemplate = worksheetTemplate;
    this.multipartFile = multipartFile;
  }

  public WorksheetTemplate getWorksheetTemplate() {
    return worksheetTemplate;
  }

  public MultipartFile getMultipartFile() {
    return multipartFile;
  }
}
