package com.wenox.uploading.service.restorer;

import com.wenox.uploading.domain.template.RestoreMode;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseRestoreService {

  void restore(final String dumpPath, final String databaseName, final RestoreMode restoreMode) throws IOException, InterruptedException, TimeoutException;
}