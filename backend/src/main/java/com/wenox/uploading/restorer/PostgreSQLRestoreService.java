package com.wenox.uploading.restorer;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLRestoreService implements DatabaseRestoreService {

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLRestoreService.class);

  @Value("${POSTGRES_IP_ADDRESS:localhost}")
  private String postgresIpAddress;

  @Value("${server.environment.cloud}")
  private Boolean isRunningOnCloud;

  @Value("${POSTGRES_HOST_PORT:5007}")
  private String postgresHostPort;

  public void restore(String dumpPath, String databaseName) throws IOException, InterruptedException, TimeoutException {

    log.info("Restoring new {} using dump located at {}.", databaseName, dumpPath);

    if (isRunningOnCloud) {

      ProcessExecutorFactory.newProcess(
          "createdb",
          "-h", postgresIpAddress,
          "-U", "postgres", "--no-password",
          "-T", "template0",
          databaseName
      ).execute();

      ProcessExecutorFactory.newProcess(
          "pg_restore",
          "-h", postgresIpAddress,
          "-U", "postgres", "--no-password",
          "-d", databaseName,
          "-v",
          dumpPath
      ).execute();

    } else {

      ProcessExecutorFactory.newProcess(
          "createdb",
          "-h", postgresIpAddress,
          "-p", postgresHostPort,
          "-U", "postgres", "--no-password",
          "-T", "template0",
          databaseName
      ).execute();

      ProcessExecutorFactory.newProcess(
          "pg_restore",
          "-h", postgresIpAddress,
          "-p", postgresHostPort,
          "-U", "postgres", "--no-password",
          "-d", databaseName,
          "-v",
          dumpPath
      ).execute();
    }

    log.info("Restored new {} successfully.", databaseName);
  }
}
