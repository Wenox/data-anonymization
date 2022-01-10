package com.wenox.uploading.restorer;

import static com.wenox.uploading.template.domain.RestoreMode.ARCHIVE;


import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.uploading.template.domain.RestoreMode;
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

  public void restore(String dumpPath, String databaseName, RestoreMode restoreMode)
      throws IOException, InterruptedException, TimeoutException {

    log.info("Restoring new {} using dump located at {}.", databaseName, dumpPath);

    switch (restoreMode) {
      case ARCHIVE -> restoreFromArchive(dumpPath, databaseName);
      case SCRIPT -> restoreFromScript(dumpPath, databaseName);
    }

    log.info("Restored new {} successfully.", databaseName);
  }

  private void restoreFromArchive(String dumpPath, String databaseName)
      throws IOException, InterruptedException, TimeoutException {
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
  }

  private void restoreFromScript(String dumpPath, String databaseName)
      throws IOException, InterruptedException, TimeoutException {
    System.out.println("Restore from script");
    if (isRunningOnCloud) {

      ProcessExecutorFactory.newProcess(
          "createdb",
          "-h", postgresIpAddress,
          "-U", "postgres", "--no-password",
          "-T", "template0",
          databaseName
      ).execute();

      ProcessExecutorFactory.newProcess(
              "psql",
              "-h", postgresIpAddress,
              "-U", "postgres", "--no-password",
              "-d", databaseName,
              "--echo-all",
              "-v", "ON_ERROR_STOP=1",
              "-f", dumpPath)
          .execute();

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
              "psql",
              "-h", postgresIpAddress,
              "-p", postgresHostPort,
              "-U", "postgres", "--no-password",
              "-d", databaseName,
              "--echo-all",
              "-v", "ON_ERROR_STOP=1",
              "-f", dumpPath)
          .execute();
    }
  }

}
