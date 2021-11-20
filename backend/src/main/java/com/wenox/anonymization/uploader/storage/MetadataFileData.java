package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;

@Deprecated
public class MetadataFileData extends FileData {
  WorksheetTemplateMetadata metadata;

  public WorksheetTemplateMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(WorksheetTemplateMetadata metadata) {
    this.metadata = metadata;
  }
}
