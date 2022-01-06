package com.wenox.processing.service.mirror;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public interface DatabaseMirrorFacade {

  String cloneDatabase(String databaseName) throws IOException, InterruptedException, TimeoutException;
}
