package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.DatabaseConnection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLMirrorFacade implements DatabaseMirrorFacade {

  private final DatabaseMirrorService mirrorService;
  private final DatabaseHostDetails hostDetails;
  private final DatabaseDisconnectService disconnectService;

  public PostgreSQLMirrorFacade(PostgreSQLMirrorService mirrorService,
                                PostgreSQLHostDetails hostDetails,
                                PostgreSQLDisconnectService disconnectService) {
    this.mirrorService = mirrorService;
    this.hostDetails = hostDetails;
    this.disconnectService = disconnectService;
  }

  @Override
  public String cloneDatabase(String databaseName) throws IOException, InterruptedException, TimeoutException {
    disconnectService.disconnect(DatabaseConnection.newPostgreSQLConnection(databaseName));
    return mirrorService.cloneDatabase(hostDetails, databaseName);
  }
}
