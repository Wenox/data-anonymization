package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLMirrorService implements DatabaseMirrorService {

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLMirrorService.class);

  @Override
  public String cloneDatabase(DatabaseHostDetails details, String databaseName) throws IOException, InterruptedException, TimeoutException {

    String newDatabaseName = "db-" + UUID.randomUUID();

    log.info("Cloning {} into {}.", databaseName, newDatabaseName);

    if (details.isRunningOnCloud()) {
      ProcessExecutorFactory.newProcess(
          "createdb",
          "-h", details.getIpAddress(),
          "-U", "postgres", "--no-password",
          "-T", databaseName,
          newDatabaseName
      ).execute();
    } else {
      ProcessExecutorFactory.newProcess(
          "createdb",
          "-h", details.getIpAddress(),
          "-p", details.getHostPort(),
          "-U", "postgres", "--no-password",
          "-T", databaseName,
          newDatabaseName
      ).execute();
    }

    log.info("Cloned successfully.");

    return newDatabaseName;
  }
}
