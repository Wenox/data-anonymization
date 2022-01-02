package com.wenox.uploading.storage.event;

import com.wenox.users.domain.FileType;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.uploading.template.domain.TemplateStatus;
import com.wenox.uploading.restorer.DatabaseRestorer;
import com.wenox.uploading.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.uploading.restorer.event.DatabaseRestoreSuccessEvent;
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
