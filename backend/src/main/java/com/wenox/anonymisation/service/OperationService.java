package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.Operation;
import com.wenox.anonymisation.dto.operations.AddOperationRequest;
import com.wenox.anonymisation.dto.operations.ColumnOperations;
import com.wenox.anonymisation.dto.operations.ColumnOperationsForTableResponse;
import com.wenox.anonymisation.dto.operations.OperationDto;
import com.wenox.anonymisation.repository.OperationRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.uploading.extractor.domain.metadata.Table;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationService {

  private final AuthService authService;
  private final WorksheetRepository worksheetRepository;
  private final OperationRepository operationRepository;

  public OperationService(AuthService authService, WorksheetRepository worksheetRepository,
                          OperationRepository operationRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.operationRepository = operationRepository;
  }

  public ColumnOperationsForTableResponse getOperationsForWorksheet(String id, String tableName, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }
    final var metadata = worksheet.getTemplate().getMetadata();

    final Table table = metadata.getTable(tableName);
    final List<Column> columns = table.getColumns().values().stream().toList();

    final List<Operation> operations = operationRepository.findAllByWorksheetAndTableName(worksheet, tableName);


    final Map<String, List<Operation>> operationsByColumn =
        operations.stream().collect(Collectors.groupingBy(Operation::getColumnName));


    List<ColumnOperations> columnOperations = new ArrayList<>();
    for (var column : columns) {
      var item = new ColumnOperations();
      item.setColumn(column);
      item.setOperations(operationsByColumn
          .getOrDefault(column.getColumnName(), List.of())
          .stream()
          .map(OperationDto::from)
          .toList());
      columnOperations.add(item);
    }

    return new ColumnOperationsForTableResponse(table.getTableName(), table.getNumberOfRows(), columnOperations);
  }

  @Transactional
  public ApiResponse addOperationForColumn(String id, AddOperationRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    List<Operation> operations =
        operationRepository.findAllByWorksheetAndTableNameAndColumnName(worksheet, dto.getTableName(),
            dto.getColumnName());

    System.out.println("Operations size for column: " + dto.getColumnName() + " of table " + dto.getTableName() + ": " + operations.size());

    if (operations
        .stream()
        .anyMatch(operation -> operation.getOperationName().equals(dto.getOperationName()))) {
      return ApiResponse.ofError("Operation " + dto.getOperationName() + " already exists.");
    }

    var operation = new Operation();
    operation.setWorksheet(worksheet);
    operation.setOperationName(dto.getOperationName());
    operation.setTableName(dto.getTableName());
    operation.setColumnName(dto.getColumnName());
    operationRepository.save(operation);
    return ApiResponse.ofSuccess("Added operation successfully");
  }
}
