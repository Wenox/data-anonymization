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

  public void restorePostgresDatabase(final String templateName, final String dbName)
      throws IOException, InterruptedException, TimeoutException {

    final String dbPath = templatesPath + templateName;

    ProcessExecutorFactory.newProcess("createdb", "-h", "anonymisation_postgres_db", "-U", "postgres", "--no-password", "-T", "template0", dbName).execute();
    ProcessExecutorFactory.newProcess("pg_restore",  "-h", "anonymisation_postgres_db", "-U", "postgres", "--no-password", "-d", dbName, "-v", dbPath).execute();
  }
}
