package com.wenox.uploading.restorer.event;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.uploading.template.domain.Template;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.uploading.template.domain.TemplateStatus;
import com.wenox.uploading.extractor.event.MetadataExtractedEvent;
import com.wenox.uploading.extractor.MetadataExtractor;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
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
  public void onDatabaseRestoreSuccessEvent(DatabaseRestoreSuccessEvent event) {
    DatabaseConnection connection = DatabaseConnection.newPostgreSQLConnection(event.getTemplate().getTemplateDatabaseName());
    Template template = event.getTemplate();

    try {
      log.info("Starting to extract metadata...");
      final TemplateMetadata metadata = metadataExtractor.extractMetadata(connection);
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
    log.error("Database restore failure event: {}.", event.getException().toString());
  }
}
