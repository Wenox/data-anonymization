package com.wenox.processing.service.listeners;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.DatabaseAnonymisedEvent;
import com.wenox.processing.domain.events.AnonymisationScriptPopulatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnonymisationScriptPopulatedListener {

  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  private static final Logger log = LoggerFactory.getLogger(AnonymisationScriptPopulatedListener.class);

  @Value("${POSTGRES_IP_ADDRESS:localhost}")
  private String postgresIpAddress;

  @Value("${server.environment.cloud}")
  private Boolean isRunningOnCloud;

  @Value("${POSTGRES_HOST_PORT:5007}")
  private String postgresHostPort;

  public AnonymisationScriptPopulatedListener(OutcomeRepository outcomeRepository,
                                              ApplicationEventPublisher applicationEventPublisher) {
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  public void onScriptPopulated(AnonymisationScriptPopulatedEvent event) {

    Outcome outcome = event.getOutcome();

    log.info("Executing anonymisation script {} into {} using psql.", event.getScriptPathLocation().toString(), outcome.getMirrorDatabaseName());
    try {
      if (isRunningOnCloud) {
        ProcessExecutorFactory.newProcess(
            "psql",
            "-h", postgresIpAddress,
            "-U", "postgres", "--no-password",
            "-d", outcome.getMirrorDatabaseName(),
            "--echo-all",
            "-v", "ON_ERROR_STOP=1",
            "-f", event.getScriptPathLocation().toString()
        ).execute();
      } else {
        ProcessExecutorFactory.newProcess(
            "psql",
            "-h", postgresIpAddress,
            "-p", postgresHostPort,
            "-U", "postgres", "--no-password",
            "-d", outcome.getMirrorDatabaseName(),
            "-v", "ON_ERROR_STOP=1",
            "--echo-all",
            "-f", event.getScriptPathLocation().toString()
        ).execute();
      }
    } catch (Exception ex) {
      log.error("Failed to execute anonymisation script {} for database {}.", event.getScriptPathLocation().toString(),
          outcome.getMirrorDatabaseName());
      ex.printStackTrace();
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_EXECUTION_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    log.info("Successfully executed anonymisation script {} for database {}.", event.getScriptPathLocation().toString(),
        outcome.getMirrorDatabaseName());
    outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_EXECUTION_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new DatabaseAnonymisedEvent(outcome));
  }
}
