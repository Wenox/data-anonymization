package com.wenox.processing.service;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.listeners.OutcomeGenerationStartedListener;
import com.wenox.processing.service.listeners.events.MirrorReadyEvent;
import com.wenox.processing.service.listeners.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.service.mirror.DatabaseMirrorFacade;
import com.wenox.processing.service.mirror.PostgreSQLMirrorFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMirrorCreator implements OutcomeGenerationStartedListener {

  private final DatabaseMirrorFacade mirrorFacade;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  private static final Logger log = LoggerFactory.getLogger(DatabaseMirrorCreator.class);

  public DatabaseMirrorCreator(PostgreSQLMirrorFacade postgreSQLMirrorService,
                               OutcomeRepository outcomeRepository,
                               ApplicationEventPublisher applicationEventPublisher) {
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
      outcomeRepository.save(outcome);
    } catch (Exception ex) {
      log.error("Failed to create mirror database of {}.", outcome.getTemplateDatabaseName());
      ex.printStackTrace();
      outcome.setOutcomeStatus(OutcomeStatus.MIRROR_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    applicationEventPublisher.publishEvent(new MirrorReadyEvent(outcome));
  }
}
