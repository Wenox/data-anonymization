package com.wenox.anonymization.core.service;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {

  @Value("${POSTGRES_IP_ADDRESS:localhost}")
  private String postgresIpAddress;

  @Value("${server.environment.cloud}")
  private Boolean isRunningOnCloud;

  @Value("${POSTGRES_HOST_PORT:5000}")
  private String postgresHostPort;

  @Value("${POSTGRES_CONTAINER_PORT:5432}")
  private String postgresContainerPort;

  public DataSource getDataSource(final ConnectionDetails connectionDetails) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    switch (connectionDetails.getDatabaseType()) {
      case PSQL:
        dataSource.setDriverClassName("org.postgresql.Driver");
        if (isRunningOnCloud) {
          dataSource.setUrl("jdbc:postgresql://" + postgresIpAddress + ":" + postgresContainerPort + "/" + connectionDetails.getDatabaseName());
        } else {
          dataSource.setUrl("jdbc:postgresql://" + postgresIpAddress + ":" + postgresHostPort + "/" + connectionDetails.getDatabaseName());
        }
        break;
      case MYSQL:
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        if (isRunningOnCloud) {
          dataSource.setUrl("jdbc:mysql://" + postgresIpAddress + ":" + postgresContainerPort + "/" + connectionDetails.getDatabaseName());
        } else {
          dataSource.setUrl("jdbc:mysql://" + postgresIpAddress + ":" + postgresHostPort + "/" + connectionDetails.getDatabaseName());
        }
      default:
        throw new RuntimeException("Unsupported database type: " + connectionDetails.getDatabaseName());
    }

    dataSource.setUsername(connectionDetails.getUsername());
    dataSource.setPassword(connectionDetails.getPassword());

    return dataSource;
  }
}
