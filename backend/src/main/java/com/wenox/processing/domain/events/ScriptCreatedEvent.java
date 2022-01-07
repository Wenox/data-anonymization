package com.wenox.processing.domain.events;

import com.wenox.processing.domain.Outcome;

public class ScriptCreatedEvent {

  private final Outcome outcome;

  public ScriptCreatedEvent(Outcome outcome) {
    this.outcome = outcome;
  }

  public Outcome getOutcome() {
    return outcome;
  }
}
