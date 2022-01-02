package com.wenox.uploading.restorer.event;

import com.wenox.uploading.template.domain.Template;

public class DatabaseRestoreSuccessEvent {

  private final Template template;

  public DatabaseRestoreSuccessEvent(final Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }
}
