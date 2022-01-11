package com.wenox.uploading.service.listeners.events;

import com.wenox.uploading.domain.template.Template;

public class MetadataExtractedEvent {

  private final Template template;

  public MetadataExtractedEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
