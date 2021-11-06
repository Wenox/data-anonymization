package com.wenox.anonymization;

import java.io.IOException;
import java.time.LocalTime;
import java.util.concurrent.TimeoutException;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRestorer {

  @EventListener
  public void onFileUploadedSuccess(FileController.FileUploadedSuccessEvent event)
      throws IOException, InterruptedException, TimeoutException {
    System.out.println("file uploaded success");

    final var file = event.getFile();
    final String dbName = "dbname_" + LocalTime.now().toString();
    final String dbPath = "E:/anon/data-anonymization/stored_files/" + file.getSavedName();
    System.out.println("dbPath: " + dbPath);

    System.out.println("now: " + LocalTime.now().toString());

    ProcessExecutorFactory.newProcess("java", "--version").execute();
    ProcessExecutorFactory.newProcess("createdb", "-U", "postgres", "-T", "template0", dbName).execute();
    ProcessExecutorFactory.newProcess("pg_restore", "-U", "postgres", "-d", dbName, "-v", dbPath).execute();

    System.out.println("Database restored successfully");
  }

  @EventListener
  public void onFileUploadedFailure(FileController.FileUploadedFailureEvent event) {
    System.out.println("file uploaded failure");
  }

}
