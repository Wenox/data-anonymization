package com.wenox.processing.service.mirror;

import com.wenox.infrastructure.service.ConnectionDetails;

public interface DatabaseDisconnectService {

  void disconnect(ConnectionDetails newPostgreSQLConnection);
}
