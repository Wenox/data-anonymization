package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.core.service.ConnectionDetails;
import com.wenox.anonymization.uploader.core.TemplateRepository;
import com.wenox.anonymization.uploader.core.TemplateStatus;
import com.wenox.anonymization.uploader.extractor.event.MetadataExtractedEvent;
import com.wenox.anonymization.uploader.extractor.MetadataExtractor;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRestoredListener {

  private static final Logger log = LoggerFactory.getLogger(DatabaseRestoredListener.class);

  private final MetadataExtractor metadataExtractor;
  private final ApplicationEventPublisher publisher;
  private final TemplateRepository repository;

  public DatabaseRestoredListener(MetadataExtractor metadataExtractor,
                                  ApplicationEventPublisher publisher,
                                  TemplateRepository repository) {
    this.metadataExtractor = metadataExtractor;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onDatabaseRestoreSuccessEvent(final DatabaseRestoreSuccessEvent event) {
    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(event.getTemplate().getType());
    connectionDetails.setDatabaseName(event.getTemplate().getDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");

    final var template = event.getTemplate();

    try {
      log.info("Starting to extract metadata...");
      final TemplateMetadata metadata = metadataExtractor.extractMetadata(connectionDetails);
      template.setStatus(TemplateStatus.METADATA_READY);
      template.setMetadata(metadata);
      log.info("Metadata extracted successfully!");
    } catch (final Exception ex) {
      template.setStatus(TemplateStatus.METADATA_FAILURE);
      template.setMetadata(null);
    }
    repository.save(template);

    publisher.publishEvent(new MetadataExtractedEvent(event.getTemplate()));
  }

  @EventListener
  public void onDatabaseRestoreFailureEvent(final DatabaseRestoreFailureEvent event) {
    System.out.println("DatabaseRestoreFailureEvent");
  }
}
