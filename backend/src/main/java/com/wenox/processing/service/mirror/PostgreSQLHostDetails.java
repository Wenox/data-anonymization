package com.wenox.processing.service.mirror;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostgreSQLHostDetails implements DatabaseHostDetails {

  private final String postgresIpAddress;
  private final String postgresHostPort;
  private final Boolean isRunningOnCloud;

  public PostgreSQLHostDetails(@Value("${POSTGRES_IP_ADDRESS:localhost}") String postgresIpAddress,
                               @Value("${POSTGRES_HOST_PORT:5007}") String postgresHostPort,
                               @Value("${server.environment.cloud}") Boolean isRunningOnCloud) {
    this.postgresIpAddress = postgresIpAddress;
    this.postgresHostPort = postgresHostPort;
    this.isRunningOnCloud = isRunningOnCloud;
  }

  @Override
  public String getIpAddress() {
    return postgresIpAddress;
  }

  @Override
  public String getHostPort() {
    return postgresHostPort;
  }

  @Override
  public Boolean isRunningOnCloud() {
    return isRunningOnCloud;
  }
}
