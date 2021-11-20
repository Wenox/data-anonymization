package com.wenox.anonymization.uploader.storage.event;

import com.wenox.anonymization.uploader.core.Template;

public class TemplateFileStoredSuccessEvent {

  private Template template;

  public TemplateFileStoredSuccessEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
