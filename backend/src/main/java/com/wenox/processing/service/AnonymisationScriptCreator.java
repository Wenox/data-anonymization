package com.wenox.processing.service;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.listeners.MirrorReadyListener;
import com.wenox.processing.service.listeners.events.MirrorReadyEvent;
import com.wenox.processing.service.listeners.events.ScriptCreatedEvent;
import com.wenox.uploading.template.domain.FileEntity;
import com.wenox.uploading.template.namegenerator.FileNameGenerator;
import com.wenox.uploading.template.namegenerator.UuidFileNameGenerator;
import com.wenox.uploading.template.repository.FileRepository;
import com.wenox.users.service.FormatDate;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnonymisationScriptCreator implements MirrorReadyListener {

  private final String anonymisationsScriptsPath;
  private final FileNameGenerator fileNameGenerator;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final OutcomeRepository outcomeRepository;
  private final FileRepository fileRepository;

  private static final Logger log = LoggerFactory.getLogger(AnonymisationScriptCreator.class);

  public AnonymisationScriptCreator(@Value("${processing.anonymisations.scripts.path}") String anonymisationsScriptsPath,
                                    UuidFileNameGenerator uuidFileNameGenerator,
                                    FileRepository fileRepository,
                                    OutcomeRepository outcomeRepository,
                                    ApplicationEventPublisher applicationEventPublisher) {
    this.anonymisationsScriptsPath = anonymisationsScriptsPath;
    this.fileNameGenerator = uuidFileNameGenerator;
    this.fileRepository = fileRepository;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  @Transactional
  public void onMirrorReadyEvent(MirrorReadyEvent event) {
    Outcome outcome = event.getOutcome();

    String savedFileName = fileNameGenerator.get();
    try {
      log.info("Initialized empty anonymisation script {} as {}.", outcome.getAnonymisationScriptName(), savedFileName);
      Files.createDirectories(Path.of(anonymisationsScriptsPath));
      Files.writeString(Path.of(anonymisationsScriptsPath, savedFileName), "-- Generated on " + FormatDate.toString(
          LocalDateTime.now()) + "\n\n");

      FileEntity anonymisationFile = new FileEntity();
      anonymisationFile.setOriginalFileName(outcome.getAnonymisationScriptName());
      anonymisationFile.setSavedFileName(savedFileName);
      anonymisationFile.setType(outcome.getTemplateType());

      fileRepository.save(anonymisationFile);
      outcome.setAnonymisationFile(anonymisationFile);
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_CREATION_SUCCESS);
      outcomeRepository.save(outcome);
    } catch (IOException ex) {
      ex.printStackTrace();
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_CREATION_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    outcomeRepository.save(outcome);
    applicationEventPublisher.publishEvent(new ScriptCreatedEvent(outcome));
  }
}
