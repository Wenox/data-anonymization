package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.DatabaseAnonymisedEvent;
import com.wenox.processing.domain.events.DatabaseDumpedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.DatabaseDumpService;
import com.wenox.processing.service.PostgreSQLDumpService;
import com.wenox.processing.service.mirror.DatabaseHostDetails;
import com.wenox.processing.service.mirror.PostgreSQLHostDetails;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAnonymisedListener {

  private final DatabaseDumpService databaseDumpService;
  private final DatabaseHostDetails databaseHostDetails;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public DatabaseAnonymisedListener(PostgreSQLDumpService postgreSQLDumpService,
                                    PostgreSQLHostDetails postgreSQLHostDetails,
                                    OutcomeRepository outcomeRepository,
                                    ApplicationEventPublisher applicationEventPublisher) {
    this.databaseDumpService = postgreSQLDumpService;
    this.databaseHostDetails = postgreSQLHostDetails;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  public void onDatabaseAnonymised(DatabaseAnonymisedEvent event) {

    Outcome outcome = event.getOutcome();

    switch (outcome.getDumpMode()) {
      case SCRIPT_FILE -> databaseDumpService.dumpDatabaseToScriptFile(databaseHostDetails, outcome.getMirrorDatabaseName(), "out.sql");
      case COMPRESSED_ARCHIVE -> databaseDumpService.dumpDatabaseToCompressedArchive(databaseHostDetails, outcome.getMirrorDatabaseName(), "out.dump");
    }

    outcome.setProcessingEndDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.DATABASE_DUMP_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new DatabaseDumpedEvent(outcome));
  }
}
