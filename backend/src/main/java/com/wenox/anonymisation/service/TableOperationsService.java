package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import com.wenox.anonymisation.dto.columnoperations.OperationDto;
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

  public TableOperationsResponse getOperationsForWorksheet(String id, String tableName, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }
    final var metadata = worksheet.getTemplate().getMetadata();

    final Table table = metadata.getTable(tableName);
    final List<Column> columns = table.getColumns().values().stream().toList();

    final List<ColumnOperations> listOfColumnOperations =
        columnOperationsRepository.findAllByWorksheetAndTableName(worksheet, tableName);


    final Map<String, List<ColumnOperations>> operationsByColumn =
        listOfColumnOperations.stream().collect(Collectors.groupingBy(ColumnOperations::getColumnName));


    List<ColumnOperationsResponse> columnOperationResponses = new ArrayList<>();
    for (var column : columns) {
      var item = new ColumnOperationsResponse();
      item.setColumn(column);
      item.setOperations(operationsByColumn
          .getOrDefault(column.getColumnName(), List.of())
          .stream()
          .map(OperationDto::from)
          .toList());
      columnOperationResponses.add(item);
    }

    return new TableOperationsResponse(table.getTableName(), table.getPrimaryKey().getColumnName(),
        table.getNumberOfRows(), columnOperationResponses);
  }

}
