package com.wenox.anonymization;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FileUploadedListener {

  private final DatabaseRestorer restorer;

  private final ApplicationEventPublisher publisher;

  public FileUploadedListener(final DatabaseRestorer restorer, final ApplicationEventPublisher publisher) {
    this.restorer = restorer;
    this.publisher = publisher;
  }

  @EventListener
  public void onFileUploadedSuccess(FileController.FileUploadedSuccessEvent event) {

    if (event.getFile().getType() != FileType.PSQL) {
      throw new RuntimeException("Unsupported file type exception");
    }

    try {
      restorer.restorePostgresDatabase(event.getFile());
      publisher.publishEvent(new DatabaseRestorer.DatabaseRestoreSuccessEvent());
    } catch (final Exception ex) {
      publisher.publishEvent(new DatabaseRestorer.DatabaseRestoreFailureEvent(ex));
    }
  }
}
