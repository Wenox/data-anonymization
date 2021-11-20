package com.wenox.anonymization.uploader.extractor.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class MetadataExtractedEvent {

  private final WorksheetTemplate template;

  public MetadataExtractedEvent(WorksheetTemplate template) {
    this.template = template;
  }

  public WorksheetTemplate getTemplate() {
    return template;
  }
}
