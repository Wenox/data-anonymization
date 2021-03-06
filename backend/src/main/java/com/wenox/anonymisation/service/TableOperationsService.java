package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import com.wenox.anonymisation.dto.columnoperations.ColumnOperationDto;
import com.wenox.anonymisation.dto.tableoperations.TableOperationsResponse;
import com.wenox.anonymisation.repository.ColumnOperationsRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.domain.metadata.Column;
import com.wenox.uploading.domain.metadata.Table;
import com.wenox.users.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TableOperationsService {

  private final AuthService authService;
  private final WorksheetRepository worksheetRepository;
  private final ColumnOperationsRepository columnOperationsRepository;

  public TableOperationsService(AuthService authService,
                                WorksheetRepository worksheetRepository,
                                ColumnOperationsRepository columnOperationsRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.columnOperationsRepository = columnOperationsRepository;
  }

  public TableOperationsResponse getTableOperations(String id, String tableName, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }
    final var metadata = worksheet.getTemplate().getMetadata();

    final Table table = metadata.getTable(tableName);
    final List<Column> columns = table.getColumns()
        .values()
        .stream()
        .toList();

    final List<ColumnOperations> operationsForTable =
        columnOperationsRepository.findOperationsForTable(worksheet, tableName);
    final Map<String, ColumnOperations> operationsByColumn =
        operationsForTable.stream().collect(Collectors.toMap(ColumnOperations::getColumnName, v -> v));


    List<ColumnOperationsResponse> listOfColumnOperationsDtos = new ArrayList<>();
    for (var column : columns) {
      var item = new ColumnOperationsResponse();
      item.setColumn(column);

      var columnOperations = operationsByColumn.get(column.getColumnName());
      if (columnOperations == null) {
        item.setListOfColumnOperation(List.of());
      } else {

        List<ColumnOperationDto> listOfColumnOperationDto = new ArrayList<>();
        Optional
            .ofNullable(columnOperations.getSuppression())
            .ifPresent(suppression -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Suppression");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(suppression.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getColumnShuffle())
            .ifPresent(columnShuffle -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("ColumnShuffle");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(columnShuffle.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getRowShuffle())
            .ifPresent(rowShuffle -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("RowShuffle");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(rowShuffle.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getPatternMasking())
            .ifPresent(patternMasking -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("PatternMasking");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(patternMasking.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getShortening())
            .ifPresent(shortening -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Shortening");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(shortening.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getGeneralisation())
            .ifPresent(generalisation -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Generalisation");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(generalisation.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getPerturbation())
            .ifPresent(perturbation -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Perturbation");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(perturbation.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getRandomNumber())
            .ifPresent(randomNumber -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("RandomNumber");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(randomNumber.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getTokenization())
            .ifPresent(tokenization -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Tokenization");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(tokenization.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getHashing())
            .ifPresent(hashing -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Hashing");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(hashing.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });
        Optional
            .ofNullable(columnOperations.getSubstitution())
            .ifPresent(substitution -> {
              ColumnOperationDto columnOperationDto = new ColumnOperationDto();
              columnOperationDto.setOperationName("Substitution");
              columnOperationDto.setColumnName(column.getColumnName());
              columnOperationDto.setTableName(tableName);
              columnOperationDto.setId(substitution.getId());
              listOfColumnOperationDto.add(columnOperationDto);
            });


        item.setListOfColumnOperation(listOfColumnOperationDto);
      }

      listOfColumnOperationsDtos.add(item);
    }

    return new TableOperationsResponse(
        table.getTableName(),
        table.getPrimaryKey().getColumnName(),
        table.getPrimaryKey().getType(),
        table.getNumberOfRows(),
        listOfColumnOperationsDtos,
        metadata
    );
  }

}
