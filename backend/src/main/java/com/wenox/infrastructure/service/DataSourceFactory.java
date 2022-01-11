package com.wenox.infrastructure.service;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component
public class DataSourceFactory {

  private final Boolean isRunningOnCloud;
  private final String postgresIpAddress;
  private final String postgresHostPort;
  private final String postgresContainerPort;

  public DataSourceFactory(@Value("${server.environment.cloud}") Boolean isRunningOnCloud,
                           @Value("${POSTGRES_IP_ADDRESS:localhost}") String postgresIpAddress,
                           @Value("${POSTGRES_HOST_PORT:5007}") String postgresHostPort,
                           @Value("${POSTGRES_CONTAINER_PORT:5432}") String postgresContainerPort) {
    this.isRunningOnCloud = isRunningOnCloud;
    this.postgresIpAddress = postgresIpAddress;
    this.postgresHostPort = postgresHostPort;
    this.postgresContainerPort = postgresContainerPort;
  }

  public DataSource getDataSource(DatabaseConnection databaseConnection) {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();

    switch (databaseConnection.getDatabaseType()) {
      case PSQL:
        dataSource.setDriverClassName("org.postgresql.Driver");
        if (isRunningOnCloud) {
          dataSource.setUrl("jdbc:postgresql://" + postgresIpAddress + ":" + postgresContainerPort + "/" + databaseConnection.getDatabaseName());
        } else {
          dataSource.setUrl("jdbc:postgresql://" + postgresIpAddress + ":" + postgresHostPort + "/" + databaseConnection.getDatabaseName());
        }
        break;
      case MYSQL:
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        if (isRunningOnCloud) {
          dataSource.setUrl("jdbc:mysql://" + postgresIpAddress + ":" + postgresContainerPort + "/" + databaseConnection.getDatabaseName());
        } else {
          dataSource.setUrl("jdbc:mysql://" + postgresIpAddress + ":" + postgresHostPort + "/" + databaseConnection.getDatabaseName());
        }
      default:
        throw new RuntimeException("Unsupported database type: " + databaseConnection.getDatabaseName());
    }

    dataSource.setUsername(databaseConnection.getUsername());
    dataSource.setPassword(databaseConnection.getPassword());

    return dataSource;
  }
}
