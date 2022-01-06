package com.wenox.processing.service;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.MirrorReadyEvent;
import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.mirror.DatabaseMirrorFacade;
import com.wenox.processing.service.mirror.PostgreSQLMirrorFacade;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OutcomeGenerationStartedListener {

  private final ApplicationEventPublisher applicationEventPublisher;
  private final OutcomeRepository outcomeRepository;
  private final DatabaseMirrorFacade mirrorFacade;

  public OutcomeGenerationStartedListener(ApplicationEventPublisher applicationEventPublisher,
                                          OutcomeRepository outcomeRepository,
                                          PostgreSQLMirrorFacade postgreSQLMirrorService) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.outcomeRepository = outcomeRepository;
    this.mirrorFacade = postgreSQLMirrorService;
  }

  @Async
  @EventListener
  public void onOutcomeGenerationStarted(OutcomeGenerationStartedEvent event) {
    Outcome outcome = event.getOutcome();
    try {
      String mirrorDatabaseName = mirrorFacade.cloneDatabase(outcome.getTemplateDatabaseName());
      outcome.setMirrorDatabaseName(mirrorDatabaseName);
      outcome.setOutcomeStatus(OutcomeStatus.MIRROR_READY);
    } catch (Exception ex) {
      outcome.setOutcomeStatus(OutcomeStatus.MIRROR_FAILURE);
      return;
    }
    outcomeRepository.save(outcome);
    applicationEventPublisher.publishEvent(new MirrorReadyEvent(outcome));
  }
}
