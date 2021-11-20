package com.wenox.anonymization.uploader.extractor.event;

import com.wenox.anonymization.uploader.core.Template;

public class MetadataExtractedEvent {

  private final Template template;

  public MetadataExtractedEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
