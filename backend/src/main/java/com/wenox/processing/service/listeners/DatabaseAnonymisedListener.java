package com.wenox.processing.service.listeners;

import com.wenox.processing.service.listeners.events.DatabaseAnonymisedEvent;

public interface DatabaseAnonymisedListener {

  void onDatabaseAnonymised(DatabaseAnonymisedEvent event);
}
