package com.wenox.uploading.template.event;

import com.wenox.storage.service.TemplateFileData;
import com.wenox.uploading.storage.event.TemplateFileStoredFailureEvent;
import com.wenox.uploading.storage.event.TemplateFileStoredSuccessEvent;
import com.wenox.uploading.template.service.FileUploader;
import com.wenox.uploading.template.service.TemplateFileUploader;
import com.wenox.uploading.template.domain.TemplateStatus;
import com.wenox.uploading.template.repository.TemplateRepository;
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

  public TemplateCreatedListener(TemplateFileUploader fileUploader,
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
      TemplateFileData fileData = new TemplateFileData(event.getFileData(), template.getType());
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
