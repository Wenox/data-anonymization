package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import com.wenox.anonymization.config.DatabaseRestoreFailureException;
import com.wenox.anonymization.uploader.core.WorksheetTemplateRepository;
import com.wenox.anonymization.uploader.extractor.event.MetadataExtractedEvent;
import com.wenox.anonymization.uploader.extractor.MetadataExtractor;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;
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
  private final WorksheetTemplateRepository repository;

  public DatabaseRestoredListener(DataSourceFactory dataSourceFactory, MetadataExtractor metadataExtractor,
                                  ApplicationEventPublisher publisher, WorksheetTemplateRepository repository) {
    this.dataSourceFactory = dataSourceFactory;
    this.metadataExtractor = metadataExtractor;
    this.publisher = publisher;
    this.repository = repository;
  }

  @EventListener
  public void onDatabaseRestoreSuccessEvent(final DatabaseRestoreSuccessEvent event) {
    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(event.getWorksheetTemplate().getType());
    connectionDetails.setDatabaseName(event.getWorksheetTemplate().getDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");

    final var worksheetTemplate = event.getWorksheetTemplate();

    try {
      final WorksheetTemplateMetadata metadata = metadataExtractor.extractMetadata(connectionDetails);
      worksheetTemplate.setStatus("METADATA_EXTRACTED_SUCCESS");
      worksheetTemplate.setMetadata(metadata);
    } catch (final Exception ex) {
      worksheetTemplate.setStatus("METADATA_EXTRACTED_FAILURE");
      worksheetTemplate.setMetadata(null);
    }
    repository.save(worksheetTemplate);

    publisher.publishEvent(new MetadataExtractedEvent(event.getWorksheetTemplate()));
  }

  @EventListener
  public void onDatabaseRestoreFailureEvent(final DatabaseRestoreFailureEvent event) {
    System.out.println("DatabaseRestoreFailureEvent");
    throw new DatabaseRestoreFailureException();
  }
}
