package com.wenox.processing.service;

import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutcomeGenerationStartedListener {

  private final ApplicationEventPublisher applicationEventPublisher;
  private final OutcomeRepository outcomeRepository;

  public OutcomeGenerationStartedListener(ApplicationEventPublisher applicationEventPublisher,
                                          OutcomeRepository outcomeRepository) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.outcomeRepository = outcomeRepository;
  }

  @Async
  @EventListener
  public void onOutcomeGenerationStarted(OutcomeGenerationStartedEvent event) {
    // create mirror.
  }
}
