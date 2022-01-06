package com.wenox.uploading.storage.event;

import com.wenox.uploading.restorer.DatabaseRestoreFacade;
import com.wenox.uploading.restorer.PostgreSQLRestoreFacade;
import com.wenox.users.domain.FileType;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.uploading.template.domain.TemplateStatus;
import com.wenox.uploading.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.uploading.restorer.event.DatabaseRestoreSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TemplateFileStoredListener {

  private final DatabaseRestoreFacade restoreFacade;
  private final ApplicationEventPublisher publisher;
  private final TemplateRepository repository;

  public TemplateFileStoredListener(PostgreSQLRestoreFacade restoreFacade,
                                    ApplicationEventPublisher publisher,
                                    TemplateRepository repository) {
    this.restoreFacade = restoreFacade;
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
      restoreFacade.restore(template.getTemplateFile().getSavedFileName(), template.getTemplateDatabaseName());
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
