package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.DatabaseConnection;

public interface DatabaseDisconnectService {

  void disconnect(DatabaseConnection newPostgreSQLConnection);
}
