package com.wenox.processing.service;

import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.anonymisation.service.ColumnShuffler;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.users.service.AuthService;
import java.time.LocalDateTime;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class OutcomeService {

  private final OutcomeRepository outcomeRepository;
  private final WorksheetRepository worksheetRepository;
  private final AuthService authService;
  private final ColumnShuffler columnShuffler;

  public OutcomeService(OutcomeRepository outcomeRepository,
                        WorksheetRepository worksheetRepository,
                        AuthService authService,
                        ColumnShuffler columnShuffler) {
    this.outcomeRepository = outcomeRepository;
    this.worksheetRepository = worksheetRepository;
    this.authService = authService;
    this.columnShuffler = columnShuffler;
  }

  public Outcome generateOutcome(String worksheetId, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(worksheetId).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    Outcome outcome = new Outcome();
    outcome.setProcessingStartDate(LocalDateTime.now());
    outcome.setWorksheet(worksheet);



    var operations = worksheet.getOperations();
    System.out.println("Operations size: " + operations.size());

    // Sort operations.

    for (var operation : operations) {
      if ("Shuffle".equals(operation.getOperationName())) {
        // 1. Get data.
        // queryForColumn operation.getPK, operation.getTableName() operation.getColumnName()

        // 2. Transform
        // columnShuffler.transform(rows);

        // 3. Generate output
        // writing into SQL file
      }
    }

    outcomeRepository.save(outcome);
    return outcome;
  }
}
