package com.wenox.anonymization;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.Arrays;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DatabaseRestorer {

  @EventListener
  public void onFileUploadedSuccess(FileController.FileUploadedSuccessEvent event)
      throws IOException, InterruptedException {
    System.out.println("file uploaded success");

    final var file = event.getFile();
    final String dbName = "dbname_" + LocalTime.now().toString();
    final String dbPath = "E:/anon/data-anonymization/stored_files/" + file.getSavedName();
    System.out.println("dbPath: " + dbPath);

    System.out.println("now: " + LocalTime.now().toString());

    runCommand("java", "--version");
    runCommand("createdb", "-U", "postgres", "-T", "template0", dbName);
    runCommand("pg_restore", "-U", "postgres", "-d", dbName, "-v", dbPath);

    System.out.println("Database restored successfully");
  }

  @EventListener
  public void onFileUploadedFailure(FileController.FileUploadedFailureEvent event) {
    System.out.println("file uploaded failure");
  }

  private void runCommand(String... cmd) throws IOException, InterruptedException {
    System.out.println("Running command: " + Arrays.toString(cmd));

    ProcessBuilder pb = new ProcessBuilder(cmd);
//    pb.environment().put("PGPASSWORD", "postgres");

    Process process = pb.start();

    System.out.println("Output stream:");
    try (BufferedReader buf = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {
      String line = buf.readLine();
      while (line != null) {
        System.out.println(line);
        line = buf.readLine();
      }
    }

    System.out.println("Error stream:");
    try (BufferedReader buf = new BufferedReader(
        new InputStreamReader(process.getErrorStream()))) {
      String line = buf.readLine();
      while (line != null) {
        System.err.println(line);
        line = buf.readLine();
      }
    }

    int exitCode = process.waitFor();
    System.out.println("Exit code: " + exitCode);
    process.destroy();
  }

}
