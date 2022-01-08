package com.wenox.processing.service;

import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.dto.GenerateOutcomeRequest;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.users.service.AuthService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OutcomeService {

  private final OutcomeRepository outcomeRepository;
  private final WorksheetRepository worksheetRepository;
  private final AuthService authService;
  private final ApplicationEventPublisher publisher;

  public OutcomeService(OutcomeRepository outcomeRepository,
                        WorksheetRepository worksheetRepository,
                        AuthService authService,
                        ApplicationEventPublisher publisher) {
    this.outcomeRepository = outcomeRepository;
    this.worksheetRepository = worksheetRepository;
    this.authService = authService;
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
    outcome.setAnonymisationScriptName(dto.getAnonymisationScriptName());
    outcome.setDumpName(dto.getDumpName());
    outcome.setDumpMode(dto.getDumpMode());
    outcome.setProcessingStartDate(LocalDateTime.now());
    outcome.setOutcomeStatus(OutcomeStatus.GENERATION_STARTED);
    outcomeRepository.save(outcome);
    publisher.publishEvent(new OutcomeGenerationStartedEvent(outcome));

    return outcome.getId();
  }

  public List<Outcome> getMyOutcomes(Authentication auth) {
    final var me = authService.getMe(auth);
    return outcomeRepository.findAllByWorksheetUser(me);
  }
}
