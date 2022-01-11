package com.wenox.processing.service.listeners.events;

import com.wenox.processing.domain.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptCreatedEvent {

  private static final Logger log = LoggerFactory.getLogger(ScriptCreatedEvent.class);

  private final Outcome outcome;

  public ScriptCreatedEvent(Outcome outcome) {
    this.outcome = outcome;
    log.info("{} for outcome {}.", this.getClass().getSimpleName(), outcome.getId());
  }

  public Outcome getOutcome() {
    return outcome;
  }
}
