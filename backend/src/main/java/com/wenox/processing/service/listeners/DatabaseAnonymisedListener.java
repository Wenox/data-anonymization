package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.DatabaseAnonymisedEvent;
import com.wenox.processing.domain.events.DatabaseDumpedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.dump.DatabaseDumpFacade;
import com.wenox.processing.service.dump.PostgreSQLDumpFacade;
import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.uploading.template.namegenerator.FileNameGenerator;
import com.wenox.uploading.template.namegenerator.UuidFileNameGenerator;
import com.wenox.uploading.template.repository.FileRepository;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseAnonymisedListener {

  private final DatabaseDumpFacade dumpFacade;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final FileRepository fileRepository;
  private final FileNameGenerator fileNameGenerator;

  public DatabaseAnonymisedListener(PostgreSQLDumpFacade postgreSQLDumpFacade,
                                    OutcomeRepository outcomeRepository,
                                    ApplicationEventPublisher applicationEventPublisher,
                                    FileRepository fileRepository,
                                    UuidFileNameGenerator uuidFileNameGenerator) {
    this.dumpFacade = postgreSQLDumpFacade;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.fileRepository = fileRepository;
    this.fileNameGenerator = uuidFileNameGenerator;
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

    switch (outcome.getDumpMode()) {
      case SCRIPT_FILE -> dumpFacade.dumpToScript(outcome.getMirrorDatabaseName(), outcome.getDumpFile().getSavedFileName());
      case COMPRESSED_ARCHIVE -> dumpFacade.dumpToArchive(outcome.getMirrorDatabaseName(), outcome.getDumpFile().getSavedFileName());
    }

    outcome.setProcessingEndDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.DATABASE_DUMP_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new DatabaseDumpedEvent(outcome));
  }
}
