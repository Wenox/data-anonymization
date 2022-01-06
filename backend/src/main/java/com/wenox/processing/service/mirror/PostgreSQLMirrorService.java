package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLMirrorService implements DatabaseMirrorService {

  @Override
  public String cloneDatabase(DatabaseHostDetails details, String dbName)
      throws IOException, InterruptedException, TimeoutException {
    System.out.println("Clonning database...");

    String newDatabaseName = UUID.randomUUID().toString();

    if (details.isRunningOnCloud()) {
      ProcessExecutorFactory.newProcess("createdb", "-h", details.getIpAddress(), "-U", "postgres", "--no-password",
          "-T",
          dbName, newDatabaseName).execute();
    } else {
      ProcessExecutorFactory.newProcess("createdb", "-h", details.getIpAddress(), "-p", details.getHostPort(),
          "-U", "postgres", "--no-password", "-T",
          dbName, newDatabaseName).execute();
    }
    System.out.println("Clone success.");
    return newDatabaseName;
  }
}
