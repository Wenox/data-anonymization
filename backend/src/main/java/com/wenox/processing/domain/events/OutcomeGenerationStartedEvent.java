package com.wenox.processing.domain.events;

import com.wenox.processing.domain.Outcome;

public class OutcomeGenerationStartedEvent {

  private final Outcome outcome;

  public OutcomeGenerationStartedEvent(Outcome outcome) {
    this.outcome = outcome;
  }

  public Outcome getOutcome() {
    return outcome;
  }
}
