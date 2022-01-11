package com.wenox.processing.service.listeners.events;

import com.wenox.processing.domain.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseAnonymisedEvent {

  private final Outcome outcome;

  private static final Logger log = LoggerFactory.getLogger(DatabaseAnonymisedEvent.class);

  public DatabaseAnonymisedEvent(Outcome outcome) {
    this.outcome = outcome;
    log.info("{} for outcome {}.", this.getClass().getSimpleName(), outcome.getId());
  }

  public Outcome getOutcome() {
    return outcome;
  }
}
