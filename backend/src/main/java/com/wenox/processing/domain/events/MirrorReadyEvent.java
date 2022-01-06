package com.wenox.processing.domain.events;

import com.wenox.processing.domain.Outcome;

public class MirrorReadyEvent {

  private final Outcome outcome;

  public MirrorReadyEvent(Outcome outcome) {
    this.outcome = outcome;
  }

  public Outcome getOutcome() {
    return outcome;
  }
}
