package com.wenox.processing.service.mirror;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseMirrorService {

  String cloneDatabase(DatabaseHostDetails details, String databaseName)
      throws IOException, InterruptedException, TimeoutException;
}
