package com.wenox.uploading.restorer;

import com.wenox.uploading.template.domain.RestoreMode;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseRestoreService {

  void restore(final String dumpPath, final String databaseName, final RestoreMode restoreMode) throws IOException, InterruptedException, TimeoutException;
}