package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.WorksheetTemplateRepository;
import com.wenox.anonymization.uploader.core.WorksheetTemplateStatus;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredFailureEvent;
import com.wenox.anonymization.uploader.storage.event.TemplateFileStoredSuccessEvent;
import com.wenox.anonymization.uploader.restorer.DatabaseRestorer;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TemplateFileStoredListener {

  private final DatabaseRestorer restorer;
  private final ApplicationEventPublisher publisher;
  private final WorksheetTemplateRepository repository;

  public TemplateFileStoredListener(DatabaseRestorer restorer,
                                    ApplicationEventPublisher publisher,
                                    WorksheetTemplateRepository repository) {
    this.restorer = restorer;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onTemplateFileStoredSuccessEvent(TemplateFileStoredSuccessEvent event) {
    final var worksheetTemplate = event.getWorksheetTemplate();

    if (worksheetTemplate.getType() != FileType.PSQL) {
      throw new RuntimeException("Unsupported file type exception");
    }

    try {
      restorer.restorePostgresDatabase(worksheetTemplate.getTemplateFile().getSavedName(), worksheetTemplate.getDatabaseName());
      worksheetTemplate.setStatus(WorksheetTemplateStatus.RESTORE_SUCCESS);
      repository.save(worksheetTemplate);
    } catch (final Exception ex) {
      worksheetTemplate.setStatus(WorksheetTemplateStatus.RESTORE_FAILURE);
      repository.save(worksheetTemplate);
      publisher.publishEvent(new DatabaseRestoreFailureEvent(ex));
      return;
    }
    publisher.publishEvent(new DatabaseRestoreSuccessEvent(worksheetTemplate));
  }

  @EventListener
  public void onTemplateFileStoredFailureEvent(TemplateFileStoredFailureEvent event) {
    System.out.println("TemplateFileStoredFailureEvent");
  }
}
