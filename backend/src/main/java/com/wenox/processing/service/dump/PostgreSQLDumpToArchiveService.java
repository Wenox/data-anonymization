package com.wenox.processing.service.dump;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.service.mirror.DatabaseHostDetails;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDumpToArchiveService implements DatabaseDumpToArchiveService {

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLDumpToArchiveService.class);

  @Override
  public void dump(DatabaseHostDetails details, String databaseName, String compressedArchivePath) {
    log.info("Dumping an anonymised database {} to compressed archive {}.", databaseName, compressedArchivePath);
    try {
      Files.createDirectories(Path.of(compressedArchivePath).getParent());
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
