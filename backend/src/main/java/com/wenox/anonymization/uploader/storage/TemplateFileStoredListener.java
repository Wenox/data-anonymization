package com.wenox.anonymization.uploader.storage;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.core.TemplateRepository;
import com.wenox.anonymization.uploader.core.TemplateStatus;
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
  private final TemplateRepository repository;

  public TemplateFileStoredListener(DatabaseRestorer restorer,
                                    ApplicationEventPublisher publisher,
                                    TemplateRepository repository) {
    this.restorer = restorer;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onTemplateFileStoredSuccessEvent(TemplateFileStoredSuccessEvent event) {
    final var template = event.getTemplate();

    if (template.getType() != FileType.PSQL) {
      throw new RuntimeException("Unsupported file type exception");
    }

    try {
      restorer.restorePostgresDatabase(template.getTemplateFile().getSavedFileName(), template.getDatabaseName());
      template.setStatus(TemplateStatus.RESTORE_SUCCESS);
      repository.save(template);
    } catch (final Exception ex) {
      template.setStatus(TemplateStatus.RESTORE_FAILURE);
      repository.save(template);
      publisher.publishEvent(new DatabaseRestoreFailureEvent(ex));
      return;
    }
    publisher.publishEvent(new DatabaseRestoreSuccessEvent(template));
  }

  @EventListener
  public void onTemplateFileStoredFailureEvent(TemplateFileStoredFailureEvent event) {
    System.out.println("TemplateFileStoredFailureEvent");
  }
}
