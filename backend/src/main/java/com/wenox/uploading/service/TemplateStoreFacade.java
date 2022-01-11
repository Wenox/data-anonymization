package com.wenox.uploading.service;

import com.wenox.storage.domain.TemplateFileData;
import com.wenox.storage.service.FileUploader;
import com.wenox.storage.service.template.TemplateFileUploader;
import com.wenox.uploading.domain.template.TemplateStatus;
import com.wenox.uploading.repository.TemplateRepository;
import com.wenox.uploading.service.listeners.TemplateCreatedListener;
import com.wenox.uploading.service.listeners.events.TemplateCreatedEvent;
import com.wenox.uploading.service.listeners.events.TemplateStoredEvent;
import java.io.IOException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TemplateStoreFacade implements TemplateCreatedListener {

  private final FileUploader fileUploader;
  private final TemplateRepository repository;
  private final ApplicationEventPublisher publisher;

  public TemplateStoreFacade(TemplateFileUploader fileUploader,
                             TemplateRepository repository,
                             ApplicationEventPublisher publisher) {
    this.fileUploader = fileUploader;
    this.publisher = publisher;
    this.repository = repository;
  }

  @Async
  @EventListener
  @Override
  public void onTemplateCreated(TemplateCreatedEvent event) {

    final var template = event.getTemplate();
    try {
      TemplateFileData fileData = new TemplateFileData(event.getFileData(), template.getType());
      final var savedTemplateFile = fileUploader.upload(fileData);
      template.setStatus(TemplateStatus.UPLOAD_SUCCESS);
      template.setTemplateFile(savedTemplateFile);
      repository.save(template);
      publisher.publishEvent(new TemplateStoredEvent(template));
    } catch (final IOException ex) {
      ex.printStackTrace();
      template.setStatus(TemplateStatus.UPLOAD_FAILURE);
      repository.save(template);
    }
  }
}
