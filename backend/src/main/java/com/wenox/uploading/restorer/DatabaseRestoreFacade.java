package com.wenox.uploading.restorer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseRestoreFacade {

  void restore(String templateName, String databaseName) throws IOException, InterruptedException, TimeoutException;
}
