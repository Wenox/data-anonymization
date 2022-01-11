package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.infrastructure.service.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDisconnectService implements DatabaseDisconnectService {

  private final DataSourceFactory dataSourceFactory;

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLDisconnectService.class);

  public PostgreSQLDisconnectService(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }

  @Override
  public void disconnect(DatabaseConnection details) {
    log.info("Disconnecting all users from {}.", details.getDatabaseName());
    JdbcTemplate template = new JdbcTemplate(dataSourceFactory.getDataSource(details));
    template.execute(getDisconnectQuery(details.getDatabaseName()));
  }

  private String getDisconnectQuery(String databaseName) {
    return String.format(
        """
        SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity
        WHERE pg_stat_activity.datname = '%s' AND pid <> pg_backend_pid()
        """, databaseName);
  }
}
