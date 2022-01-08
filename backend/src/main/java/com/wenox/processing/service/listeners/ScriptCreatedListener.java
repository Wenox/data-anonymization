package com.wenox.processing.service.listeners;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.ScriptCreatedEvent;
import com.wenox.processing.domain.events.ScriptPopulatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.QueryExecutor;
import com.wenox.processing.service.operations.SuppressionService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScriptCreatedListener {

  @Value("${processing.anonymisations.scripts.path}")
  String anonymisationsScriptsPath;

  private final DataSourceFactory dataSourceFactory;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public ScriptCreatedListener(DataSourceFactory dataSourceFactory,
                               OutcomeRepository outcomeRepository,
                               ApplicationEventPublisher applicationEventPublisher) {
    this.dataSourceFactory = dataSourceFactory;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  @Transactional
  public void onScriptCreated(ScriptCreatedEvent event) {

    Outcome outcome = event.getOutcome();
    Worksheet worksheet = outcome.getWorksheet();

    final var connectionDetails = new ConnectionDetails();
    connectionDetails.setDatabaseType(worksheet.getTemplate().getType());
    connectionDetails.setDatabaseName(worksheet.getTemplate().getTemplateDatabaseName());
    connectionDetails.setUsername("postgres");
    connectionDetails.setPassword("postgres");
    final var queryExecutor = new QueryExecutor(dataSourceFactory.getDataSource(connectionDetails));

    List<ColumnOperations> listOfColumnOperations = worksheet.getListOfColumnOperations();
    System.out.println("Lost of column operations - affected columns size: " + listOfColumnOperations.size());

    var fileEntity = outcome.getFileEntity();
    var fileLocation = Path.of(anonymisationsScriptsPath, fileEntity.getSavedFileName());

    for (ColumnOperations columnOperations : listOfColumnOperations) {

      // 1. Get data.
      // queryForColumn operation.getPK, operation.getTableName() operation.getColumnName()
      final var rows = queryExecutor.select(columnOperations.getTableName(), columnOperations.getPrimaryKeyColumnName(),
          columnOperations.getColumnName());

      // 2. Transform
      var suppression = columnOperations.getSuppression();
      if (suppression != null) {

        System.out.println("Ready to transform with suppression.");

        var suppressedRows = new SuppressionService().suppress(rows, suppression.getSuppressionToken());

        System.out.println("Suppression ended, new values:");
        suppressedRows.forEach(System.out::println);


        // 3. Generate output

        System.out.println("Opening for writing: " + fileLocation);
        for (var row : suppressedRows) {
          try {
            Files.writeString(fileLocation,
                String.format("UPDATE %s SET %s = '%s' WHERE %s = %s;\n",
                    columnOperations.getTableName(),
                    columnOperations.getColumnName(),
                    row.getSecond(),
                    columnOperations.getPrimaryKeyColumnName(),
                    row.getFirst()
                ),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

        var shuffle = columnOperations.getShuffle();
        if (shuffle != null) {

          System.out.println("Result success.");
          System.out.println("Ready to transform with shuffle.");
        }
      }

    }
    outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_POPULATION_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new ScriptPopulatedEvent(outcome, fileLocation));
  }
}
