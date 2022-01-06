package com.wenox.processing.service;

import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.OutcomeGenerationStartedEvent;
import com.wenox.processing.repository.OutcomeRepository;
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

  public String generateOutcome(String worksheetId, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(worksheetId).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(worksheet.getTemplate().getType());
    connectionDetails.setDatabaseName(worksheet.getTemplate().getTemplateDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");
    final var queryExecutor = new QueryExecutor(dataSourceFactory.getDataSource(connectionDetails));

    Outcome outcome = new Outcome();
    outcome.setProcessingStartDate(LocalDateTime.now());
    outcome.setWorksheet(worksheet);
    outcome.setOutcomeStatus(OutcomeStatus.GENERATION_STARTED);
    outcomeRepository.save(outcome);
    publisher.publishEvent(new OutcomeGenerationStartedEvent(outcome));

    return outcome.getId();
  }

//    List<ColumnOperations> listOfColumnOperations = worksheet.getListOfColumnOperations();
//    System.out.println("Lost of column operations - affected columns size: " + listOfColumnOperations.size());
//
//
//    for (ColumnOperations columnOperations : listOfColumnOperations) {
//
//      final var rows = queryExecutor.select(columnOperations.getTableName(), columnOperations.getPrimaryKeyColumnName(), columnOperations.getColumnName());
//
//      var suppression = columnOperations.getSuppression();
//      if (suppression != null) {
//
//        System.out.println("Ready to transform with suppression.");
//
//        var suppressed = new SuppressionService().suppress(rows, suppression.getSuppressionToken());
//
//        System.out.println("Suppression ended, new values:");
//        suppressed.forEach(System.out::println);
//
//
//        // 1. Get data.
//        // queryForColumn operation.getPK, operation.getTableName() operation.getColumnName()
//
//        // 2. Transform
//        // columnShuffler.transform(rows);
//
//        // 3. Generate output
//        // writing into SQL file
//      }
//
//
//
//      var shuffle = columnOperations.getShuffle();
//      if (shuffle != null) {
//
//        System.out.println("Result success.");
//        System.out.println("Ready to transform with shuffle.");
//
//        // 1. Get data.
//        // queryForColumn operation.getPK, operation.getTableName() operation.getColumnName()
//
//        // 2. Transform
//        // columnShuffler.transform(rows);
//
//        // 3. Generate output
//        // writing into SQL file
//      }
//
//
//    }
//
//    outcomeRepository.save(outcome);
//  }
}
