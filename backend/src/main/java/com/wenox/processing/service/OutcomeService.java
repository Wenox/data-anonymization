package com.wenox.processing.service;

import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.dto.GenerateOutcomeRequest;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.operations.ColumnShuffler;
import com.wenox.users.service.AuthService;
import java.time.LocalDateTime;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OutcomeService {

  private final DataSourceFactory dataSourceFactory;
  private final OutcomeRepository outcomeRepository;
  private final WorksheetRepository worksheetRepository;
  private final AuthService authService;
  private final ColumnShuffler columnShuffler;
  private final ApplicationEventPublisher publisher;

  public OutcomeService(DataSourceFactory dataSourceFactory,
                        OutcomeRepository outcomeRepository,
                        WorksheetRepository worksheetRepository,
                        AuthService authService,
                        ColumnShuffler columnShuffler,
                        ApplicationEventPublisher publisher) {
    this.dataSourceFactory = dataSourceFactory;
    this.outcomeRepository = outcomeRepository;
    this.worksheetRepository = worksheetRepository;
    this.authService = authService;
    this.columnShuffler = columnShuffler;
    this.publisher = publisher;
  }

  public String generateOutcome(GenerateOutcomeRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(dto.getWorksheetId()).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    Outcome outcome = new Outcome();
    outcome.setWorksheet(worksheet);
    outcome.setScriptName(dto.getScriptName());
    outcome.setDumpMode(dto.getDumpMode());
    outcome.setProcessingStartDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.GENERATION_STARTED);
    outcomeRepository.save(outcome);
    publisher.publishEvent(new OutcomeGenerationStartedEvent(outcome));

    return outcome.getId();
  }
}
