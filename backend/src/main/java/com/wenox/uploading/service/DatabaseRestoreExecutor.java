package com.wenox.uploading.service;

import com.wenox.uploading.domain.template.TemplateStatus;
import com.wenox.uploading.repository.TemplateRepository;
import com.wenox.uploading.service.listeners.TemplateStoredListener;
import com.wenox.uploading.service.listeners.events.DatabaseRestoredEvent;
import com.wenox.uploading.service.listeners.events.TemplateStoredEvent;
import com.wenox.uploading.service.restorer.DatabaseRestoreFacade;
import com.wenox.uploading.service.restorer.PostgreSQLRestoreFacade;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRestoreExecutor implements TemplateStoredListener {

  private final DatabaseRestoreFacade restoreFacade;
  private final TemplateRepository repository;
  private final ApplicationEventPublisher publisher;

  public DatabaseRestoreExecutor(PostgreSQLRestoreFacade restoreFacade,
                                 TemplateRepository repository,
                                 ApplicationEventPublisher publisher) {
    this.restoreFacade = restoreFacade;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onTemplateStored(TemplateStoredEvent event) {
    final var template = event.getTemplate();
    try {
      restoreFacade.restore(template.getTemplateFile().getSavedFileName(), template.getTemplateDatabaseName(), template.getRestoreMode());
      template.setStatus(TemplateStatus.RESTORE_SUCCESS);
      repository.save(template);
    } catch (final Exception ex) {
      template.setStatus(TemplateStatus.RESTORE_FAILURE);
      repository.save(template);
      return;
    }
    publisher.publishEvent(new DatabaseRestoredEvent(template));
  }
}
