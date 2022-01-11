package com.wenox.uploading.service.listeners;

import com.wenox.uploading.service.listeners.events.DatabaseRestoredEvent;

public interface DatabaseRestoredListener {

 void onDatabaseRestored(DatabaseRestoredEvent event);
}
