package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.uploader.core.WorksheetTemplate;

public class MetadataExtractedEvent {

  private final WorksheetTemplate template;
  private final DatabaseMetadataExtractor.WorksheetTemplateMetadata metadata;

  public MetadataExtractedEvent(WorksheetTemplate template,
                                DatabaseMetadataExtractor.WorksheetTemplateMetadata metadata) {
    this.template = template;
    this.metadata = metadata;
  }

  public WorksheetTemplate getTemplate() {
    return template;
  }

  public DatabaseMetadataExtractor.WorksheetTemplateMetadata getMetadata() {
    return metadata;
  }
}
