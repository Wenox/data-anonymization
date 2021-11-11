package com.wenox.anonymization.uploader.restorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import com.wenox.anonymization.config.DatabaseRestoreFailureException;
import com.wenox.anonymization.uploader.extractor.MetadataExtractedEvent;
import com.wenox.anonymization.uploader.extractor.MetadataExtractor;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRestoredListener {

  private final DataSourceFactory dataSourceFactory;
  private final MetadataExtractor metadataExtractor;
  private final ApplicationEventPublisher publisher;

  public DatabaseRestoredListener(DataSourceFactory dataSourceFactory, MetadataExtractor metadataExtractor,
                                  ApplicationEventPublisher publisher) {
    this.dataSourceFactory = dataSourceFactory;
    this.metadataExtractor = metadataExtractor;
    this.publisher = publisher;
  }

  @EventListener
  public void onDatabaseRestoreSuccessEvent(final DatabaseRestoreSuccessEvent event) throws SQLException {
    System.out.println("DatabaseRestoreSuccessEvent inside");

    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(event.getWorksheetTemplate().getType());
    connectionDetails.setDatabaseName(event.getWorksheetTemplate().getDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");

    final var metadata = metadataExtractor.extractMetadata(connectionDetails);
    publisher.publishEvent(new MetadataExtractedEvent(event.getWorksheetTemplate(), metadata));
  }

  @EventListener
  public void onDatabaseRestoreFailureEvent(final DatabaseRestoreFailureEvent event) {
    System.out.println("DatabaseRestoreFailureEvent");
    throw new DatabaseRestoreFailureException();
  }
}
