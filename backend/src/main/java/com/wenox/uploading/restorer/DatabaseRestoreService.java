package com.wenox.uploading.restorer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseRestoreService {

  void restore(final String dumpPath, final String databaseName) throws IOException, InterruptedException, TimeoutException;
}