package com.wenox.uploading.storage.event;

import com.wenox.uploading.template.domain.Template;

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
