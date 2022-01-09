package com.wenox.processing.service.listeners;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.domain.events.ScriptCreatedEvent;
import com.wenox.processing.domain.events.AnonymisationScriptPopulatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.Query;
import com.wenox.processing.service.QueryExecutor;
import com.wenox.processing.service.operations.ColumnShuffler;
import com.wenox.processing.service.operations.PatternMaskingService;
import com.wenox.processing.service.operations.RowShuffler;
import com.wenox.processing.service.operations.ShorteningService;
import com.wenox.processing.service.operations.SuppressionService;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Types;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnonymisationScriptCreatedListener {

  @Value("${processing.anonymisations.scripts.path}")
  String anonymisationsScriptsPath;

  private final DataSourceFactory dataSourceFactory;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public AnonymisationScriptCreatedListener(DataSourceFactory dataSourceFactory,
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

    var fileEntity = outcome.getAnonymisationFile();
    var fileLocation = Path.of(anonymisationsScriptsPath, fileEntity.getSavedFileName());

    for (ColumnOperations columnOperations : listOfColumnOperations) {

      // 1. Get data.
      final var rows = queryExecutor.select(columnOperations.getTableName(), columnOperations.getPrimaryKeyColumnName(),
          columnOperations.getColumnName());

      // 2. Transform
      var suppression = columnOperations.getSuppression();
      if (suppression != null) {
        var suppressedRows = new SuppressionService().suppress(rows, suppression.getSuppressionToken());

        // 3.0 Handle column type change
        if (!columnOperations.getColumnType().equals(String.valueOf(Types.VARCHAR))) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.ALTER_COLUMN_TYPE)
                    .tableName(columnOperations.getTableName())
                    .columnName(columnOperations.getColumnName())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }

        // 3. Generate output
        for (var row : suppressedRows) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.UPDATE)
                    .tableName(columnOperations.getTableName())
                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
                    .primaryKeyValue(row.getFirst())
                    .columnName(columnOperations.getColumnName())
                    .columnType(String.valueOf(Types.VARCHAR))
                    .columnValue(row.getSecond())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }

      var columnShuffle = columnOperations.getColumnShuffle();
      if (columnShuffle != null) {
        final List<Pair<String, String>> shuffledRows;
        if (columnShuffle.isWithRepetitions()) {
          shuffledRows = new ColumnShuffler().shuffleWithRepetitions(rows);
        } else {
          shuffledRows = new ColumnShuffler().shuffle(rows);
        }

        for (var row : shuffledRows) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.UPDATE)
                    .tableName(columnOperations.getTableName())
                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
                    .primaryKeyValue(row.getFirst())
                    .columnName(columnOperations.getColumnName())
                    .columnType(columnOperations.getColumnType())
                    .columnValue(row.getSecond())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }


      var rowShuffle = columnOperations.getRowShuffle();
      if (rowShuffle != null) {
        final List<Pair<String, String>> shuffledRows;
        if (rowShuffle.isWithRepetitions()) {
          shuffledRows = new RowShuffler().shuffleWithRepetitions(rows, rowShuffle.getLetterMode());
        } else {
          shuffledRows = new RowShuffler().shuffle(rows, rowShuffle.getLetterMode());
        }

        for (var row : shuffledRows) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.UPDATE)
                    .tableName(columnOperations.getTableName())
                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
                    .primaryKeyValue(row.getFirst())
                    .columnName(columnOperations.getColumnName())
                    .columnType(columnOperations.getColumnType())
                    .columnValue(row.getSecond())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }


      var patternMasking = columnOperations.getPatternMasking();
      if (patternMasking != null) {
        final List<Pair<String, String>> maskedRows = new PatternMaskingService().mask(rows, patternMasking);

        for (var row : maskedRows) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.UPDATE)
                    .tableName(columnOperations.getTableName())
                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
                    .primaryKeyValue(row.getFirst())
                    .columnName(columnOperations.getColumnName())
                    .columnType(columnOperations.getColumnType())
                    .columnValue(row.getSecond())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }


      var shortening = columnOperations.getShortening();
      if (shortening != null) {
        final List<Pair<String, String>> shortenedRows = new ShorteningService().shorten(rows, shortening);

        for (var row : shortenedRows) {
          try {
            Files.writeString(fileLocation,
                new Query.QueryBuilder(Query.QueryType.UPDATE)
                    .tableName(columnOperations.getTableName())
                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
                    .primaryKeyValue(row.getFirst())
                    .columnName(columnOperations.getColumnName())
                    .columnType(columnOperations.getColumnType())
                    .columnValue(row.getSecond())
                    .build()
                    .toString(),
                StandardOpenOption.APPEND);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }


    }
    outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_POPULATION_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new AnonymisationScriptPopulatedEvent(outcome, fileLocation));
  }
}
