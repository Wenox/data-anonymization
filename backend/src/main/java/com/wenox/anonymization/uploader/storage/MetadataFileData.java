package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;

@Deprecated
public class MetadataFileData extends FileData {
  TemplateMetadata metadata;

  public TemplateMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(TemplateMetadata metadata) {
    this.metadata = metadata;
  }
}
