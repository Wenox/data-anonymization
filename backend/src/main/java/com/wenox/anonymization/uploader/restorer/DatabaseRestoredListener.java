package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import com.wenox.anonymization.config.DatabaseRestoreFailureException;
import com.wenox.anonymization.uploader.core.TemplateRepository;
import com.wenox.anonymization.uploader.core.TemplateStatus;
import com.wenox.anonymization.uploader.extractor.event.MetadataExtractedEvent;
import com.wenox.anonymization.uploader.extractor.MetadataExtractor;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRestoredListener {

  private final DataSourceFactory dataSourceFactory;
  private final MetadataExtractor metadataExtractor;
  private final ApplicationEventPublisher publisher;
  private final TemplateRepository repository;

  public DatabaseRestoredListener(DataSourceFactory dataSourceFactory, MetadataExtractor metadataExtractor,
                                  ApplicationEventPublisher publisher, TemplateRepository repository) {
    this.dataSourceFactory = dataSourceFactory;
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
      final TemplateMetadata metadata = metadataExtractor.extractMetadata(connectionDetails);
      template.setStatus(TemplateStatus.METADATA_READY);
      template.setMetadata(metadata);
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
    throw new DatabaseRestoreFailureException();
  }
}
