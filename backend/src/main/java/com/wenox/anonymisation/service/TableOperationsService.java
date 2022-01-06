package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import com.wenox.anonymisation.dto.columnoperations.ColumnOperationDto;
import com.wenox.anonymisation.dto.tableoperations.TableOperationsResponse;
import com.wenox.anonymisation.repository.ColumnOperationsRepository;
import com.wenox.anonymisation.repository.SuppressionRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.uploading.extractor.domain.metadata.Table;
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
  private final SuppressionRepository suppressionRepository;

  public TableOperationsService(AuthService authService, WorksheetRepository worksheetRepository,
                                ColumnOperationsRepository columnOperationsRepository,
                                SuppressionRepository suppressionRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.columnOperationsRepository = columnOperationsRepository;
    this.suppressionRepository = suppressionRepository;
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
        item.setListOfColumnOperation(listOfColumnOperationDto);
      }
      
      listOfColumnOperationsDtos.add(item);
    }

    return new TableOperationsResponse(table.getTableName(), table.getPrimaryKey().getColumnName(),
        table.getNumberOfRows(), listOfColumnOperationsDtos);
  }

}
