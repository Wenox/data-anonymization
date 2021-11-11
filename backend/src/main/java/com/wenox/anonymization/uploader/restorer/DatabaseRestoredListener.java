package com.wenox.anonymization.uploader.restorer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import com.wenox.anonymization.config.DatabaseRestoreFailureException;
import com.wenox.anonymization.uploader.extractor.MetadataExtractor;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreFailureEvent;
import com.wenox.anonymization.uploader.restorer.event.DatabaseRestoreSuccessEvent;
import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRestoredListener {

  private final DataSourceFactory dataSourceFactory;
  private final MetadataExtractor metadataExtractor;

  public DatabaseRestoredListener(DataSourceFactory dataSourceFactory, MetadataExtractor metadataExtractor) {
    this.dataSourceFactory = dataSourceFactory;
    this.metadataExtractor = metadataExtractor;
  }

  @EventListener
  public void onDatabaseRestoreSuccessEvent(final DatabaseRestoreSuccessEvent event) throws SQLException {
    System.out.println("DatabaseRestoreSuccessEvent inside");

    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(event.getWorksheetTemplate().getType());
    connectionDetails.setDatabaseName(event.getWorksheetTemplate().getDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");

    final DataSource dataSource = dataSourceFactory.getDataSource(connectionDetails);
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    System.out.println("Extracting metadata...");

    final var metadata = metadataExtractor.extractMetadata(connectionDetails);

    try {
      new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(new File("E:/anon/data-anonymization/out2.json"), metadata);
    } catch (Exception ex) {
      ex.printStackTrace();
    }


//    int result1 = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM songs", Integer.class);
//    System.out.println("result1: " + result1);
//    int result2 = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM public.songs", Integer.class);
//    System.out.println("result2: " + result2);




    System.out.println("metadata extracted --- all done");
  }

  @EventListener
  public void onDatabaseRestoreFailureEvent(final DatabaseRestoreFailureEvent event) {
    System.out.println("DatabaseRestoreFailureEvent");
    throw new DatabaseRestoreFailureException();
  }
}
