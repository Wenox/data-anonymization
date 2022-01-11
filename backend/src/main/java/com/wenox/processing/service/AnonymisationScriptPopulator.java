package com.wenox.processing.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.processing.domain.Outcome;
import com.wenox.processing.domain.OutcomeStatus;
import com.wenox.processing.service.listeners.events.AnonymisationScriptPopulatedEvent;
import com.wenox.processing.service.listeners.events.ScriptCreatedEvent;
import com.wenox.processing.repository.OutcomeRepository;
import com.wenox.processing.service.listeners.AnonymisationScriptCreatedListener;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationFacadeFactory;
import com.wenox.processing.service.operations.AnonymisationOperation;
import com.wenox.processing.service.query.JdbcTemplateQuerySelector;
import com.wenox.processing.service.query.QuerySelector;
import com.wenox.uploading.template.domain.FileEntity;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AnonymisationScriptPopulator implements AnonymisationScriptCreatedListener {

  private final String anonymisationsScriptsPath;
  private final AnonymisationFacadeFactory anonymisationFacadeFactory;
  private final DataSourceFactory dataSourceFactory;
  private final OutcomeRepository outcomeRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public AnonymisationScriptPopulator(@Value("${processing.anonymisations.scripts.path}") String anonymisationsScriptsPath,
                                      AnonymisationFacadeFactory anonymisationFacadeFactory,
                                      DataSourceFactory dataSourceFactory,
                                      OutcomeRepository outcomeRepository,
                                      ApplicationEventPublisher applicationEventPublisher) {
    this.anonymisationsScriptsPath = anonymisationsScriptsPath;
    this.anonymisationFacadeFactory = anonymisationFacadeFactory;
    this.dataSourceFactory = dataSourceFactory;
    this.outcomeRepository = outcomeRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  @Transactional
  public void onScriptCreated(ScriptCreatedEvent event) {
    Outcome outcome = event.getOutcome();
    Worksheet worksheet = outcome.getWorksheet();

    DatabaseConnection connection = DatabaseConnection.newPostgreSQLConnection(worksheet.getTemplate().getTemplateDatabaseName());
    QuerySelector selector = new JdbcTemplateQuerySelector(dataSourceFactory.getDataSource(connection));

    List<ColumnOperations> listOfColumnOperations = worksheet.getListOfColumnOperations();

    FileEntity file = outcome.getAnonymisationFile();
    Path fileLocation = Path.of(anonymisationsScriptsPath, file.getSavedFileName());

    try {
      for (ColumnOperations operations : listOfColumnOperations) {
        final var rows = selector.select(operations.getTableName(), operations.getPrimaryKeyColumnName(), operations.getColumnName());
        for (AnonymisationOperation operation : AnonymisationOperation.values()) {
          AnonymisationFacade facade = anonymisationFacadeFactory.getFacade(operation);
          facade.handleAnonymisation(operations, rows, fileLocation);
        }
      }
    } catch (Exception ex) {
      outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_POPULATION_FAILURE);
      outcomeRepository.save(outcome);
      return;
    }

    outcome.setOutcomeStatus(OutcomeStatus.SCRIPT_POPULATION_SUCCESS);
    outcomeRepository.save(outcome);

    applicationEventPublisher.publishEvent(new AnonymisationScriptPopulatedEvent(outcome, fileLocation));
  }
}
