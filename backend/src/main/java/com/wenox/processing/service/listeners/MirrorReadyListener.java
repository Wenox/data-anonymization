package com.wenox.processing.service.listeners;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.MirrorReadyEvent;
import com.wenox.processing.domain.events.ScriptCreatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.uploading.restorer.PostgreSQLRestoreService;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MirrorReadyListener {

  @Value("${processing.scripts.path}")
  String scriptsPath;

  private final ApplicationEventPublisher applicationEventPublisher;
  private final OutcomeRepository outcomeRepository;

  private static final Logger log = LoggerFactory.getLogger(MirrorReadyListener.class);

  public MirrorReadyListener(ApplicationEventPublisher applicationEventPublisher,
                             OutcomeRepository outcomeRepository) {
    this.applicationEventPublisher = applicationEventPublisher;
    this.outcomeRepository = outcomeRepository;
  }

  @Async
  @EventListener
  public void onMirrorReadyEvent(MirrorReadyEvent event) {
    Outcome outcome = event.getOutcome();

    try {
      log.info("Creating script {}.", "script.sql");
      Files.createDirectories(Path.of(scriptsPath));
      Files.writeString(Path.of(scriptsPath, "script.sql"), "-- Generated on " + FormatDate.toString(LocalDateTime.now()) + "\n");
      outcome.setScriptName("script.sql");
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_CREATION_SUCCESS);
      outcomeRepository.save(outcome);
    } catch (IOException ex) {
      ex.printStackTrace();
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_CREATION_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    System.out.println("saving outcome.. " + outcome.getOutcomeStatus());
    outcomeRepository.save(outcome);
    applicationEventPublisher.publishEvent(new ScriptCreatedEvent(outcome));
  }
}
