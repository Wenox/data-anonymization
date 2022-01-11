package com.wenox.uploading.service;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.uploading.domain.metadata.TemplateMetadata;
import com.wenox.uploading.domain.template.Template;
import com.wenox.uploading.domain.template.TemplateStatus;
import com.wenox.uploading.repository.TemplateRepository;
import com.wenox.uploading.service.extractor.MetadataExtractor;
import com.wenox.uploading.service.listeners.DatabaseRestoredListener;
import com.wenox.uploading.service.listeners.events.DatabaseRestoredEvent;
import com.wenox.uploading.service.listeners.events.MetadataExtractedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MetadataExtractorFacade implements DatabaseRestoredListener {

  private final MetadataExtractor metadataExtractor;
  private final TemplateRepository repository;
  private final ApplicationEventPublisher publisher;

  public MetadataExtractorFacade(MetadataExtractor metadataExtractor,
                                 TemplateRepository repository,
                                 ApplicationEventPublisher publisher) {
    this.metadataExtractor = metadataExtractor;
    this.repository = repository;
    this.publisher = publisher;
  }

  @EventListener
  @Override
  public void onDatabaseRestored(DatabaseRestoredEvent event) {

    DatabaseConnection connection = DatabaseConnection.newPostgreSQLConnection(event.getTemplate().getTemplateDatabaseName());
    Template template = event.getTemplate();

    try {
      final TemplateMetadata metadata = metadataExtractor.extractMetadata(connection);
      template.setStatus(TemplateStatus.METADATA_READY);
      template.setMetadata(metadata);
    } catch (final Exception ex) {
      template.setStatus(TemplateStatus.METADATA_FAILURE);
      template.setMetadata(null);
    }
    repository.save(template);

    publisher.publishEvent(new MetadataExtractedEvent(event.getTemplate()));
  }
}
