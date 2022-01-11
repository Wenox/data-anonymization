package com.wenox.uploading.service.listeners.events;

import com.wenox.uploading.domain.template.Template;

public class DatabaseRestoredEvent {

  private final Template template;

  public DatabaseRestoredEvent(final Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
