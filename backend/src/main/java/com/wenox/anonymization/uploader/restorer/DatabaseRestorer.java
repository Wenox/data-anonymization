package com.wenox.anonymization.uploader.restorer;

import com.wenox.anonymization.uploader.core.FileEntity;
import com.wenox.anonymization.commons.ProcessExecutorFactory;
import com.wenox.anonymization.uploader.core.event.FileUploadedFailureEvent;
import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRestorer {

  public void restorePostgresDatabase(final FileEntity file)
      throws IOException, InterruptedException, TimeoutException {

    final String dbName = "dbname_" + LocalTime.now().toString();
    final String dbPath = "E:/anon/data-anonymization/stored_files/" + file.getSavedName();
    System.out.println("dbPath: " + dbPath);

    ProcessExecutorFactory.newProcess("createdb", "-U", "postgres", "-T", "template0", dbName).execute();
    ProcessExecutorFactory.newProcess("pg_restore", "-U", "postgres", "-d", dbName, "-v", dbPath).execute();
  }
}
