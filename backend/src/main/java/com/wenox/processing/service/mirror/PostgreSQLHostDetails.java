package com.wenox.processing.service.mirror;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PostgreSQLHostDetails implements DatabaseHostDetails {

  @Value("${POSTGRES_IP_ADDRESS:localhost}")
  private String postgresIpAddress;

  @Value("${POSTGRES_HOST_PORT:5007}")
  private String postgresHostPort;

  @Value("${server.environment.cloud}")
  private Boolean isRunningOnCloud;

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
