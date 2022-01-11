package com.wenox.processing.service.listeners;

import com.wenox.processing.service.listeners.events.OutcomeGenerationStartedEvent;

public interface OutcomeGenerationStartedListener {

  void onOutcomeGenerationStarted(OutcomeGenerationStartedEvent event);
}
