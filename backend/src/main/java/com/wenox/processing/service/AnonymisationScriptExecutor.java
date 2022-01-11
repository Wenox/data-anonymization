package com.wenox.processing.service;

import com.wenox.infrastructure.service.ProcessExecutorFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.DatabaseAnonymisedEvent;
import com.wenox.processing.domain.events.AnonymisationScriptPopulatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.listeners.AnonymisationScriptPopulatedListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AnonymisationScriptExecutor implements AnonymisationScriptPopulatedListener {

  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final Boolean isRunningOnCloud;
  private final String postgresIpAddress;
  private final String postgresHostPort;

  private static final Logger log = LoggerFactory.getLogger(AnonymisationScriptExecutor.class);

  public AnonymisationScriptExecutor(OutcomeRepository outcomeRepository,
                                     ApplicationEventPublisher applicationEventPublisher,
                                     @Value("${server.environment.cloud}") Boolean isRunningOnCloud,
                                     @Value("${POSTGRES_IP_ADDRESS:localhost}") String postgresIpAddress,
                                     @Value("${POSTGRES_HOST_PORT:5007}") String postgresHostPort) {
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.postgresIpAddress = postgresIpAddress;
    this.postgresHostPort = postgresHostPort;
    this.isRunningOnCloud = isRunningOnCloud;
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

