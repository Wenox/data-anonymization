package com.wenox.uploading.service.listeners.events;

import com.wenox.uploading.domain.template.Template;

public class TemplateStoredEvent {

  private Template template;

  public TemplateStoredEvent(Template template) {
    this.template = template;
  }

  public Template getTemplate() {
    return template;
  }

  public void setTemplate(Template template) {
    this.template = template;
  }
}
