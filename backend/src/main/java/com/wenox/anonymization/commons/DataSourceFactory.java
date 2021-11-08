package com.wenox.anonymization.commons;

import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {

  public DataSource getDataSource(final ConnectionDetails connectionDetails) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    switch (connectionDetails.getDatabaseType()) {
      case PSQL:
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost/" + connectionDetails.getDatabaseName());
        break;
      case MYSQL:
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost/" + connectionDetails.getDatabaseName());
      default:
        throw new RuntimeException("Unsupported database type: " + connectionDetails.getDatabaseName());
    }

    dataSource.setUsername(connectionDetails.getUsername());
    dataSource.setPassword(connectionDetails.getPassword());

    return dataSource;
  }
}
