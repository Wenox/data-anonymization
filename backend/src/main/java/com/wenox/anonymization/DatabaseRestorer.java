package com.wenox.anonymization;

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

  @EventListener
  public void onFileUploadedFailure(FileController.FileUploadedFailureEvent event) {
    System.out.println("file uploaded failure");
    System.out.println("Exception message: " + event.getException().getMessage());
  }

  static class DatabaseRestoreSuccessEvent {

  }

  static class DatabaseRestoreFailureEvent {
    private final Exception ex;

    public DatabaseRestoreFailureEvent(Exception ex) {
      this.ex = ex;
    }


  }

}
