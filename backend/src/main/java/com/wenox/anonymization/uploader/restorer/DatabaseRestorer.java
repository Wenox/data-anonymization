package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.core.service.ProcessExecutorFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRestorer {

  @Value("${uploader.templates.path}")
  private String templatesPath;

  @Value("${POSTGRES_IP_ADDRESS:localhost}")
  private String postgresIpAddress;

  @Value("${server.environment.cloud}")
  private Boolean isRunningOnCloud;

  @Value("${POSTGRES_HOST_PORT:5000}")
  private String postgresHostPort;

  public void restorePostgresDatabase(final String templateName, final String dbName)
      throws IOException, InterruptedException, TimeoutException {

    final String dbPath = templatesPath + "/" + templateName;

    if (isRunningOnCloud) {
      ProcessExecutorFactory.newProcess("createdb", "-h", postgresIpAddress, "-U", "postgres", "--no-password", "-T",
          "template0", dbName).execute();
      ProcessExecutorFactory.newProcess("pg_restore", "-h", postgresIpAddress, "-U", "postgres", "--no-password", "-d",
          dbName, "-v", dbPath).execute();
    } else {
      ProcessExecutorFactory.newProcess("createdb", "-h", postgresIpAddress, "-p", postgresHostPort, "-U", "postgres",
          "--no-password", "-T", "template0", dbName).execute();
      ProcessExecutorFactory.newProcess("pg_restore", "-h", postgresIpAddress, "-p", postgresHostPort, "-U", "postgres",
          "--no-password", "-d", dbName, "-v", dbPath).execute();
    }
  }
}
