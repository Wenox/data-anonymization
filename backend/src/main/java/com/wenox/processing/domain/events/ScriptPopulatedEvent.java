package com.wenox.processing.domain.events;

import com.wenox.processing.domain.Outcome;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptPopulatedEvent {

  private final Outcome outcome;
  private final Path scriptPathLocation;

  private static final Logger log = LoggerFactory.getLogger(OutcomeGenerationStartedEvent.class);

  public ScriptPopulatedEvent(Outcome outcome, Path scriptPathLocation) {
    this.outcome = outcome;
    this.scriptPathLocation = scriptPathLocation;
    log.info("{} for outcome {}.", this.getClass().getSimpleName(), outcome.getId());
  }

  public Outcome getOutcome() {
    return outcome;
  }

  public Path getScriptPathLocation() {
    return scriptPathLocation;
  }
}
