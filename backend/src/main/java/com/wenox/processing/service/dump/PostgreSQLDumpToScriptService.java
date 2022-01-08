package com.wenox.processing.service.dump;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.service.mirror.DatabaseHostDetails;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDumpToScriptService implements DatabaseDumpToScriptService {

  private static final Logger log = LoggerFactory.getLogger(PostgreSQLDumpToScriptService.class);

  @Override
  public void dump(DatabaseHostDetails details, String databaseName, String scriptFilePath) {
    log.info("Dumping an anonymised database {} to script file {}.", databaseName, scriptFilePath);
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
}
