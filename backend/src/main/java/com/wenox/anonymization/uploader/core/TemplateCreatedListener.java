package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.storage.TemplateFileData;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredFailureEvent;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredSuccessEvent;
import com.wenox.anonymization.uploader.core.event.TemplateCreatedEvent;
import java.io.IOException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TemplateCreatedListener {

  private final FileUploader fileUploader;
  private final ApplicationEventPublisher publisher;
  private final TemplateRepository repository;

  public TemplateCreatedListener(MultipartFileUploader fileUploader,
                                 ApplicationEventPublisher publisher,
                                 TemplateRepository repository) {
    this.fileUploader = fileUploader;
    this.publisher = publisher;
    this.repository = repository;
  }

  @Async
  @EventListener
  public void onTemplateCreated(TemplateCreatedEvent event) {

    final var template = event.getTemplate();
    try {
      TemplateFileData fileData = new TemplateFileData();
      fileData.setFileDTO(event.getFileDTO());
      fileData.setFileType(template.getType());
      final var savedTemplateFile = fileUploader.upload(fileData);
      template.setStatus(TemplateStatus.UPLOAD_SUCCESS);
      template.setTemplateFile(savedTemplateFile);
      repository.save(template);
      publisher.publishEvent(new TemplateFileStoredSuccessEvent(template));
    } catch (final IOException ex) {
      ex.printStackTrace();
      template.setStatus(TemplateStatus.UPLOAD_FAILURE);
      repository.save(template);
      publisher.publishEvent(new TemplateFileStoredFailureEvent(template));
    }
  }
}
