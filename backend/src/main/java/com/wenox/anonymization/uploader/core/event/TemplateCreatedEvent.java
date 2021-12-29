package com.wenox.anonymization.uploader.core.event;

import com.wenox.anonymization.uploader.core.FileDto;
import com.wenox.anonymization.uploader.core.Template;

public class TemplateCreatedEvent {

  private final Template template;
  private final FileDto fileDTO;

  public TemplateCreatedEvent(Template template, FileDto fileDTO) {
    this.template = template;
    this.fileDTO = fileDTO;
  }

  public Template getTemplate() {
    return template;
  }

  public FileDto getFileDTO() {
    return fileDTO;
  }
}
