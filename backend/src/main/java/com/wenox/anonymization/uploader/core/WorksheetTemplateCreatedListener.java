package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.WorksheetTemplateCreatedEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import com.wenox.anonymization.uploader.restorer.DatabaseRestorer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class WorksheetTemplateCreatedListener {

  private final DatabaseRestorer restorer;
  private final ApplicationEventPublisher publisher;
  private final WorksheetTemplateRepository repository;

  public WorksheetTemplateCreatedListener(final DatabaseRestorer restorer, final ApplicationEventPublisher publisher,
                                          final WorksheetTemplateRepository repository) {
    this.restorer = restorer;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onWorksheetTemplateCreated(WorksheetTemplateCreatedEvent event) {

    final var worksheetTemplate = event.getWorksheetTemplate();

    if (worksheetTemplate.getType() != FileType.PSQL) {
      throw new RuntimeException("Unsupported file type exception");
    }

    try {
      restorer.restorePostgresDatabase(worksheetTemplate.getTemplateFile().getSavedName(), worksheetTemplate.getDatabaseName());
      worksheetTemplate.setStatus("DATABASE_RESTORE_SUCCESS"); // todo: enum
      repository.save(worksheetTemplate);
    } catch (final Exception ex) {
      worksheetTemplate.setStatus("DATABASE_RESTORE_FAILURE"); // todo: enum
      repository.save(worksheetTemplate);
      publisher.publishEvent(new DatabaseRestoreFailureEvent(ex));
      return;
    }
    publisher.publishEvent(new DatabaseRestoreSuccessEvent(worksheetTemplate));
  }
}
