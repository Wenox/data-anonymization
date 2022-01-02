package com.wenox.uploading.template.event;

import com.wenox.uploading.template.dto.FileDto;
import com.wenox.uploading.template.domain.Template;

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
