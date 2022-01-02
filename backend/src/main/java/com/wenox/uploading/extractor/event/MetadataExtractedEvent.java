package com.wenox.uploading.extractor.event;

import com.wenox.uploading.template.domain.Template;

public class MetadataExtractedEvent {

  private final Template template;

  public MetadataExtractedEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
