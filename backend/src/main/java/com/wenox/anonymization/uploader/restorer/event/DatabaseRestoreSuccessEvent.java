package com.wenox.anonymization.uploader.restorer.event;

import com.wenox.anonymization.uploader.core.Template;

public class DatabaseRestoreSuccessEvent {

  private final Template template;

  public DatabaseRestoreSuccessEvent(final Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
