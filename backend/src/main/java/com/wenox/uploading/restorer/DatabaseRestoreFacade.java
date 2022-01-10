package com.wenox.uploading.restorer;

import com.wenox.uploading.template.domain.RestoreMode;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseRestoreFacade {

  void restore(String templateName, String databaseName, RestoreMode restoreMode) throws IOException, InterruptedException, TimeoutException;
}
