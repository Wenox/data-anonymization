package com.wenox.processing.service.listeners.events;

import com.wenox.processing.domain.Outcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseDumpedEvent {

  private final Outcome outcome;

  private static final Logger log = LoggerFactory.getLogger(DatabaseDumpedEvent.class);

  public DatabaseDumpedEvent(Outcome outcome) {
    this.outcome = outcome;
    log.info("{} for outcome {}.", this.getClass().getSimpleName(), outcome.getId());
  }

  public Outcome getOutcome() {
    return outcome;
  }
}