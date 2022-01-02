package com.wenox.uploading.storage.event;

import com.wenox.uploading.template.domain.Template;

public class TemplateFileStoredFailureEvent {

  private Template template;

  public TemplateFileStoredFailureEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
