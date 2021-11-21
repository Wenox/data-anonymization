package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.core.service.ProcessExecutorFactory;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRestorer {

  public void restorePostgresDatabase(final String templateName, final String dbName)
      throws IOException, InterruptedException, TimeoutException {

    final String dbPath = "E:/anon/data-anonymization/stored_files/" + templateName;

    ProcessExecutorFactory.newProcess("createdb", "-U", "postgres", "-T", "template0", dbName).execute();
    ProcessExecutorFactory.newProcess("pg_restore", "-U", "postgres", "-d", dbName, "-v", dbPath).execute();
  }
}
