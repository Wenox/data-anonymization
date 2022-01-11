package com.wenox.processing.service;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.dump.DatabaseDumpFacade;
import com.wenox.processing.service.dump.PostgreSQLDumpFacade;
import com.wenox.processing.service.listeners.DatabaseAnonymisedListener;
import com.wenox.processing.service.listeners.events.DatabaseAnonymisedEvent;
import com.wenox.processing.service.listeners.events.DatabaseDumpedEvent;
import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.uploading.template.namegenerator.FileNameGenerator;
import com.wenox.uploading.template.namegenerator.UuidFileNameGenerator;
import com.wenox.uploading.template.repository.FileRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDumpExecutor implements DatabaseAnonymisedListener {

  private final DatabaseDumpFacade dumpFacade;
  private final FileNameGenerator fileNameGenerator;
  private final FileRepository fileRepository;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  private static final Logger log = LoggerFactory.getLogger(DatabaseDumpExecutor.class);

  public DatabaseDumpExecutor(PostgreSQLDumpFacade postgreSQLDumpFacade,
                              UuidFileNameGenerator uuidFileNameGenerator,
                              FileRepository fileRepository,
                              OutcomeRepository outcomeRepository,
                              ApplicationEventPublisher applicationEventPublisher) {
    this.dumpFacade = postgreSQLDumpFacade;
    this.fileNameGenerator = uuidFileNameGenerator;
    this.fileRepository = fileRepository;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  public void onDatabaseAnonymised(DatabaseAnonymisedEvent event) {

    Outcome outcome = event.getOutcome();

    FileEntity dumpFile = new FileEntity();
    dumpFile.setType(outcome.getTemplateType());
    dumpFile.setOriginalFileName(outcome.getDumpName());
    dumpFile.setSavedFileName(fileNameGenerator.get());
    outcome.setDumpFile(dumpFile);
    fileRepository.save(dumpFile);

    try {
      log.info("Dumping mirror database {} to {}.", outcome.getMirrorDatabaseName(), outcome.getDumpFile());
      switch (outcome.getDumpMode()) {
        case SCRIPT_FILE -> dumpFacade.dumpToScript(outcome.getMirrorDatabaseName(), outcome.getDumpFile().getSavedFileName());
        case COMPRESSED_ARCHIVE -> dumpFacade.dumpToArchive(outcome.getMirrorDatabaseName(), outcome.getDumpFile().getSavedFileName());
      }
    } catch (Exception ex) {
      log.error("Failed to dump mirror database {} to {}.", outcome.getMirrorDatabaseName(), outcome.getDumpFile().getSavedFileName());
      ex.printStackTrace();
      outcome.setProcessingEndDate(LocalDateTime.now());
      outcome.setOutcomeStatus(OutcomeStatus.DATABASE_DUMP_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    outcome.setProcessingEndDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.DATABASE_DUMP_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new DatabaseDumpedEvent(outcome));
  }
}