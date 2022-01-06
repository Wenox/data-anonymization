package com.wenox.uploading.restorer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLRestoreFacade implements DatabaseRestoreFacade {

  private final DatabaseRestoreService restoreService;

  @Value("${uploader.templates.path}")
  private String templatesPath;

  public PostgreSQLRestoreFacade(PostgreSQLRestoreService restoreService) {
    this.restoreService = restoreService;
  }

  public void restore(String templateName, String databaseName) throws IOException, InterruptedException, TimeoutException {
    final String dumpPath = templatesPath + "/" + templateName;
    restoreService.restore(dumpPath, databaseName);
  }
}
