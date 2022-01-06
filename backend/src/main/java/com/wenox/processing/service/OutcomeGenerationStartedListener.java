package com.wenox.processing.service;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.mirror.DatabaseMirrorFacade;
import com.wenox.processing.service.mirror.DatabaseMirrorService;
import com.wenox.processing.service.mirror.PostgreSQLMirrorFacade;
import com.wenox.processing.service.mirror.PostgreSQLMirrorService;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
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
  public void onOutcomeGenerationStarted(OutcomeGenerationStartedEvent event)
      throws IOException, InterruptedException, TimeoutException {
    // create mirror.

    final var dbName = event.getOutcome().getWorksheet().getTemplate().getDatabaseName();
    System.out.println("dbName as template: " + dbName);

    System.out.println("Creating mirror...");

    var newDb = mirrorFacade.cloneDatabase(dbName);

    System.out.println("new db name success: " + newDb);
    System.out.println("Mirror created success!");

  }
}
