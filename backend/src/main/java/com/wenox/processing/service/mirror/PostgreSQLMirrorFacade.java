package com.wenox.processing.service.mirror;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLMirrorFacade implements DatabaseMirrorFacade {

  private final DatabaseMirrorService mirrorService;
  private final DatabaseHostDetails hostDetails;

  public PostgreSQLMirrorFacade(PostgreSQLMirrorService mirrorService,
                                PostgreSQLHostDetails hostDetails) {
    this.mirrorService = mirrorService;
    this.hostDetails = hostDetails;
  }

  @Override
  public String cloneDatabase(String databaseName) throws IOException, InterruptedException, TimeoutException {
    return mirrorService.cloneDatabase(hostDetails, databaseName);
  }
}
