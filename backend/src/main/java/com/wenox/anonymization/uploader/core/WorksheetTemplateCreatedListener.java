package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.storage.TemplateFileData;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredFailureEvent;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredSuccessEvent;
import com.wenox.anonymization.uploader.core.event.WorksheetTemplateCreatedEvent;
import java.io.IOException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WorksheetTemplateCreatedListener {

  private final FileUploader fileUploader;
  private final ApplicationEventPublisher publisher;
  private final WorksheetTemplateRepository repository;

  public WorksheetTemplateCreatedListener(MultipartFileUploader fileUploader,
                                          ApplicationEventPublisher publisher,
                                          WorksheetTemplateRepository repository) {
    this.fileUploader = fileUploader;
    this.publisher = publisher;
    this.repository = repository;
  }

  @Async
  @EventListener
  public void onWorksheetTemplateCreated(WorksheetTemplateCreatedEvent event) {

    final var worksheetTemplate = event.getWorksheetTemplate();
    try {
      TemplateFileData fileData = new TemplateFileData();
      fileData.setFileDTO(event.getFileDTO());
      fileData.setFileType(worksheetTemplate.getType());
      final var savedTemplateFile = fileUploader.upload(fileData);
      worksheetTemplate.setStatus("TEMPLATE_UPLOAD_SUCCESS"); // todo: enum
      worksheetTemplate.setTemplateFile(savedTemplateFile);
      repository.save(worksheetTemplate);
      publisher.publishEvent(new TemplateFileStoredSuccessEvent(worksheetTemplate));
    } catch (final IOException ex) {
      ex.printStackTrace();
      worksheetTemplate.setStatus("TEMPLATE_UPLOAD_FAILURE");
      repository.save(worksheetTemplate);
      publisher.publishEvent(new TemplateFileStoredFailureEvent(worksheetTemplate));
    }
  }
}
