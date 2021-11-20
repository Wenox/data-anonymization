package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.FileDTO;
import com.wenox.anonymization.uploader.core.Template;

public class TemplateCreatedEvent {

  private final Template template;
  private final FileDTO fileDTO;

  public TemplateCreatedEvent(Template template, FileDTO fileDTO) {
    this.template = template;
    this.fileDTO = fileDTO;
  }

  public Template getTemplate() {
    return template;
  }

  public FileDTO getFileDTO() {
    return fileDTO;
  }
}
