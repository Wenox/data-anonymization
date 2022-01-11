package com.wenox.uploading.service.restorer;

import com.wenox.uploading.domain.template.RestoreMode;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLRestoreFacade implements DatabaseRestoreFacade {

  private final DatabaseRestoreService restoreService;
  private final String templatesPath;

  public PostgreSQLRestoreFacade(PostgreSQLRestoreService restoreService,
                                 @Value("${uploader.templates.path}") String templatesPath) {
    this.restoreService = restoreService;
    this.templatesPath = templatesPath;
  }

  @Override
  public void restore(String templateName, String databaseName, RestoreMode restoreMode) throws IOException, InterruptedException, TimeoutException {
    final String dumpPath = templatesPath + "/" + templateName;
    restoreService.restore(dumpPath, databaseName, restoreMode);
  }
}
