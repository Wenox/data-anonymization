package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.DatabaseAnonymisedEvent;
import com.wenox.processing.domain.events.DatabaseDumpedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.dump.DatabaseDumpFacade;
import com.wenox.processing.service.dump.PostgreSQLDumpFacade;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAnonymisedListener {

  private final DatabaseDumpFacade dumpFacade;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public DatabaseAnonymisedListener(PostgreSQLDumpFacade postgreSQLDumpFacade,
                                    OutcomeRepository outcomeRepository,
                                    ApplicationEventPublisher applicationEventPublisher) {
    this.dumpFacade = postgreSQLDumpFacade;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  public void onDatabaseAnonymised(DatabaseAnonymisedEvent event) {

    Outcome outcome = event.getOutcome();

    switch (outcome.getDumpMode()) {
      case SCRIPT_FILE -> dumpFacade.dumpToScript(outcome.getMirrorDatabaseName(), outcome.getDumpName());
      case COMPRESSED_ARCHIVE -> dumpFacade.dumpToArchive(outcome.getMirrorDatabaseName(), outcome.getDumpName());
    }

    outcome.setProcessingEndDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.DATABASE_DUMP_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new DatabaseDumpedEvent(outcome));
  }
}
