package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.events.ScriptCreatedEvent;

public interface AnonymisationScriptCreatedListener {

   void onScriptCreated(ScriptCreatedEvent event);
}
