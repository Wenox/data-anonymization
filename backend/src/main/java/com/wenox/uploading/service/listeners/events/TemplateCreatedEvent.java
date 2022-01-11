package com.wenox.uploading.service.listeners.events;

import com.wenox.storage.domain.FileData;
import com.wenox.uploading.domain.template.Template;

public class TemplateCreatedEvent {

  private final Template template;
  private final FileData fileData;

  public TemplateCreatedEvent(Template template, FileData fileData) {
    this.template = template;
    this.fileData = fileData;
  }

  public Template getTemplate() {
    return template;
  }

  public FileData getFileData() {
    return fileData;
  }
}
