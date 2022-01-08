package com.wenox.processing.service;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.service.mirror.DatabaseHostDetails;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDumpService implements DatabaseDumpService {

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLDumpService.class);

  @Override
  public void dumpDatabaseToScriptFile(DatabaseHostDetails details, String databaseName, String scriptFilePath) {
    log.info("Dumping to script file anonymised database {}.", databaseName);
    try {
      if (details.isRunningOnCloud()) {
        try (var outputStream = new FileOutputStream(scriptFilePath)) {
          ProcessExecutorFactory.newProcessWithOutputRedirectedTo(
              outputStream,
              "pg_dump",
              "-h", details.getIpAddress(),
              "-U", "postgres", "--no-password",
              "-Fp",
              databaseName
          ).execute();
        }
      } else {
        try (var outputStream = new FileOutputStream(scriptFilePath)) {
          ProcessExecutorFactory.newProcessWithOutputRedirectedTo(
              outputStream,
              "pg_dump",
              "-h", details.getIpAddress(),
              "-p", details.getHostPort(),
              "-U", "postgres", "--no-password",
              "-Fp",
              databaseName
          ).execute();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  @Override
  public void dumpDatabaseToCompressedArchive(DatabaseHostDetails details, String databaseName, String compressedArchivePath) {
    log.info("Dumping to compressed archive anonymised database {}.", databaseName);
    try {
      if (details.isRunningOnCloud()) {
        try (var outputStream = new FileOutputStream(compressedArchivePath)) {
          ProcessExecutorFactory.newProcessWithOutputRedirectedTo(
              outputStream,
              "pg_dump",
              "-h", details.getIpAddress(),
              "-U", "postgres", "--no-password",
              "-Fc",
              databaseName
          ).execute();
        }
      } else {
        try (var outputStream = new FileOutputStream(compressedArchivePath)) {
          ProcessExecutorFactory.newProcessWithOutputRedirectedTo(
              outputStream,
              "pg_dump",
              "-h", details.getIpAddress(),
              "-p", details.getHostPort(),
              "-U", "postgres", "--no-password",
              "-Fc",
              databaseName
          ).execute();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
