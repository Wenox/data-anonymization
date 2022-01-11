package com.wenox.processing.service.listeners;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.domain.events.AnonymisationScriptPopulatedEvent;
import com.wenox.processing.domain.events.ScriptCreatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationFacadeFactory;
import com.wenox.processing.service.operations.AnonymisationOperation;
import com.wenox.processing.service.query.QueryExecutor;
import java.nio.file.Path;
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
  private final AnonymisationFacadeFactory anonymisationFacadeFactory;

  public AnonymisationScriptCreatedListener(DataSourceFactory dataSourceFactory,
                                            OutcomeRepository outcomeRepository,
                                            ApplicationEventPublisher applicationEventPublisher,
                                            AnonymisationFacadeFactory anonymisationFacadeFactory) {
    this.dataSourceFactory = dataSourceFactory;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
    this.anonymisationFacadeFactory = anonymisationFacadeFactory;
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

    for (ColumnOperations operations : listOfColumnOperations) {

      final var rows = queryExecutor.select(operations.getTableName(), operations.getPrimaryKeyColumnName(), operations.getColumnName());

      for (AnonymisationOperation operation : AnonymisationOperation.values()) {
        System.out.println("Handling: " + operation);
        AnonymisationFacade facade = anonymisationFacadeFactory.getFacade(operation);
        facade.handleAnonymisation(operations, rows, fileLocation);
      }

      // 2. Transform
//      var suppression = columnOperations.getSuppression();
//      if (suppression != null) {
//        var suppressedRows = new SuppressionService().anonymise(rows, suppression);
//
//        // 3.0 Handle column type change
//        if (!columnOperations.getColumnType().equals(String.valueOf(Types.VARCHAR))) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.ALTER_COLUMN_TYPE_TEXT)
//                    .tableName(columnOperations.getTableName())
//                    .columnName(columnOperations.getColumnName())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//
//        // 3. Generate output
//        for (var row : suppressedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(String.valueOf(Types.VARCHAR))
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }



//      var columnShuffle = columnOperations.getColumnShuffle();
//      if (columnShuffle != null) {
//        final List<Pair<String, String>> shuffledRows = new ColumnShuffleService().anonymise(rows, columnShuffle);
//
//        for (var row : shuffledRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }


//      var rowShuffle = columnOperations.getRowShuffle();
//      if (rowShuffle != null) {
//        final List<Pair<String, String>> shuffledRows = new RowShuffleService().anonymise(rows, rowShuffle);
//
//        for (var row : shuffledRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }


//      var patternMasking = columnOperations.getPatternMasking();
//      if (patternMasking != null) {
//        final List<Pair<String, String>> maskedRows = new PatternMaskingService().anonymise(rows, patternMasking);
//
//        for (var row : maskedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }


//      var shortening = columnOperations.getShortening();
//      if (shortening != null) {
//        final List<Pair<String, String>> shortenedRows = new ShorteningService().anonymise(rows, shortening);
//
//        for (var row : shortenedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }



//      var generalisation = columnOperations.getGeneralisation();
//      if (generalisation != null) {
//
//        // 3.0 Handle column type change
//        if (!columnOperations.getColumnType().equals(String.valueOf(Types.VARCHAR))) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.ALTER_COLUMN_TYPE_TEXT)
//                    .tableName(columnOperations.getTableName())
//                    .columnName(columnOperations.getColumnName())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//
//        final List<Pair<String, String>> generalisedRows = new GeneralisationService().anonymise(rows, generalisation);
//
//        for (var row : generalisedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(String.valueOf(Types.VARCHAR))
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }


//      var perturbation = columnOperations.getPerturbation();
//      if (perturbation != null) {
//
//        final List<Pair<String, String>> perturbatedRows = new PerturbationService().anonymise(rows, perturbation);
//
//        for (var row : perturbatedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
//
//
//
//      var randomNumber = columnOperations.getRandomNumber();
//      if (randomNumber != null) {
//
//        final List<Pair<String, String>> randomizedRows = new RandomNumberService().anonymise(rows, randomNumber);
//
//        for (var row : randomizedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(columnOperations.getColumnType())
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
//



//      var hashing = columnOperations.getHashing();
//      if (hashing != null) {
//
//        // 3.0 Handle column type change
//        if (!columnOperations.getColumnType().equals(String.valueOf(Types.VARCHAR))) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.ALTER_COLUMN_TYPE_TEXT)
//                    .tableName(columnOperations.getTableName())
//                    .columnName(columnOperations.getColumnName())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//
//        final List<Pair<String, String>> hashedRows = new HashingService().anonymise(rows, hashing);
//
//        for (var row : hashedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(String.valueOf(Types.VARCHAR))
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
      }


//
//      var tokenization = columnOperations.getTokenization();
//      if (tokenization != null) {
//
//        // 3.0 Handle column type change
//        if (!columnOperations.getColumnType().equals(String.valueOf(Types.INTEGER))) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.ALTER_COLUMN_TYPE_INTEGER)
//                    .tableName(columnOperations.getTableName())
//                    .columnName(columnOperations.getColumnName())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//
//        final List<Pair<String, String>> tokenizedRows = new TokenizationService().anonymise(rows, tokenization);
//
//        for (var row : tokenizedRows) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(String.valueOf(Types.INTEGER))
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
//
//
//      var substitution = columnOperations.getSubstitution();
//      if (substitution != null) {
//
//        // 3.0 Handle column type change
//        if (!columnOperations.getColumnType().equals(String.valueOf(Types.VARCHAR))) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.ALTER_COLUMN_TYPE_TEXT)
//                    .tableName(columnOperations.getTableName())
//                    .columnName(columnOperations.getColumnName())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//
//        final List<Pair<String, String>> substitutions = new SubstitutionService().anonymise(rows, substitution);
//
//        for (var row : substitutions) {
//          try {
//            Files.writeString(fileLocation,
//                new Query.QueryBuilder(QueryType.UPDATE)
//                    .tableName(columnOperations.getTableName())
//                    .primaryKeyColumnName(columnOperations.getPrimaryKeyColumnName())
//                    .primaryKeyType(columnOperations.getPrimaryKeyColumnType())
//                    .primaryKeyValue(row.getFirst())
//                    .columnName(columnOperations.getColumnName())
//                    .columnType(String.valueOf(Types.VARCHAR))
//                    .columnValue(row.getSecond())
//                    .build()
//                    .toString(),
//                StandardOpenOption.APPEND);
//          } catch (Exception ex) {
//            ex.printStackTrace();
//          }
//        }
//      }
//
//    }

    outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_POPULATION_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new AnonymisationScriptPopulatedEvent(outcome, fileLocation));
  }
}
