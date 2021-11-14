package com.wenox.anonymization.uploader.extractor.event;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;

public class MetadataExtractedEvent {

  private final WorksheetTemplate template;
  private final WorksheetTemplateMetadata metadata;

  public MetadataExtractedEvent(WorksheetTemplate template,
                                WorksheetTemplateMetadata metadata) {
    this.template = template;
    this.metadata = metadata;
  }

  public WorksheetTemplate getTemplate() {
    return template;
  }

  public WorksheetTemplateMetadata getMetadata() {
    return metadata;
  }
}
