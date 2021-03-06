package com.wenox.processing.service;

import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.service.listeners.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.dto.GenerateOutcomeRequest;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.storage.service.outcome.DumpStorageFactory;
import com.wenox.users.service.AuthService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OutcomeService {

  private final OutcomeRepository outcomeRepository;
  private final WorksheetRepository worksheetRepository;
  private final AuthService authService;
  private final ApplicationEventPublisher publisher;
  private final DumpStorageFactory storageFactory;

  public OutcomeService(OutcomeRepository outcomeRepository,
                        WorksheetRepository worksheetRepository,
                        AuthService authService,
                        ApplicationEventPublisher publisher,
                        DumpStorageFactory storageFactory) {
    this.outcomeRepository = outcomeRepository;
    this.worksheetRepository = worksheetRepository;
    this.authService = authService;
    this.publisher = publisher;
    this.storageFactory = storageFactory;
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

  public byte[] downloadOutcomeDump(String id, Authentication auth) throws IOException {
    final var me = authService.getMe(auth);
    final var outcome = outcomeRepository.findById(id).orElseThrow();
    if (!me.equals(outcome.getWorksheet().getUser())) {
      throw new RuntimeException("This outcome belongs to other user!");
    }

    var dump = Optional.ofNullable(outcome.getDumpFile())
            .orElseThrow(() -> new RuntimeException("No dump file associated with this outcome!"));

    return switch (outcome.getDumpMode()) {
      case COMPRESSED_ARCHIVE -> storageFactory.getDumpArchiveStorage().retrieve(dump.getSavedFileName());
      case SCRIPT_FILE -> storageFactory.getDumpScriptStorage().retrieve(dump.getSavedFileName());
    };
  }

  public byte[] downloadAnonymisationScript(String id, Authentication auth) throws IOException {
    final var me = authService.getMe(auth);
    final var outcome = outcomeRepository.findById(id).orElseThrow();
    if (!me.equals(outcome.getWorksheet().getUser())) {
      throw new RuntimeException("This outcome belongs to other user!");
    }

    var anonymisationScript = Optional.ofNullable(outcome.getAnonymisationFile())
        .orElseThrow(() -> new RuntimeException("No anonymisation script file associated with this outcome!"));

    return storageFactory.getAnonymisationScriptStorage().retrieve(anonymisationScript.getSavedFileName());
  }
}
