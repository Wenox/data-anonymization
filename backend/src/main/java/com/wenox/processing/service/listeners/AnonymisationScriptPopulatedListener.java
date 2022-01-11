package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.events.AnonymisationScriptPopulatedEvent;

public interface AnonymisationScriptPopulatedListener {

  void onScriptPopulated(AnonymisationScriptPopulatedEvent event);
}
