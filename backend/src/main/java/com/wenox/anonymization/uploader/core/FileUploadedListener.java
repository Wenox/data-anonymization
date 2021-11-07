package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.FileUploadedSuccessEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import com.wenox.anonymization.uploader.restorer.DatabaseRestorer;
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
  public void onFileUploadedSuccess(FileUploadedSuccessEvent event) {

    if (event.getFile().getType() != FileType.PSQL) {
      throw new RuntimeException("Unsupported file type exception");
    }

    try {
      restorer.restorePostgresDatabase(event.getFile());
      publisher.publishEvent(new DatabaseRestoreSuccessEvent());
    } catch (final Exception ex) {
      publisher.publishEvent(new DatabaseRestoreFailureEvent(ex));
    }
  }
}
