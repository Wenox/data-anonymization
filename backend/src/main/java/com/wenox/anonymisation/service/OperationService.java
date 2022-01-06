package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.Operation;
import com.wenox.anonymisation.domain.Suppression;
import com.wenox.anonymisation.dto.operations.AddOperationRequest;
import com.wenox.anonymisation.dto.operations.AddSuppressionRequest;
import com.wenox.anonymisation.dto.operations.ColumnOperations;
import com.wenox.anonymisation.dto.operations.ColumnOperationsForTableResponse;
import com.wenox.anonymisation.dto.operations.OperationDto;
import com.wenox.anonymisation.repository.OperationRepository;
import com.wenox.anonymisation.repository.SuppressionRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.uploading.extractor.domain.metadata.Table;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationService {

  private final AuthService authService;
  private final WorksheetRepository worksheetRepository;
  private final OperationRepository operationRepository;
  private final SuppressionRepository suppressionRepository;

  public OperationService(AuthService authService, WorksheetRepository worksheetRepository,
                          OperationRepository operationRepository,
                          SuppressionRepository suppressionRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.operationRepository = operationRepository;
    this.suppressionRepository = suppressionRepository;
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

    return new ColumnOperationsForTableResponse(table.getTableName(), table.getPrimaryKey().getColumnName(),
        table.getNumberOfRows(), columnOperations);
  }

  @Transactional
  public ApiResponse addSuppressionOperationForColumn(String id, AddSuppressionRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    Operation operation = operationRepository.findOperationForColumn(worksheet, dto.getTableName(), dto.getColumnName())
        .orElseGet(
            () -> {
              Operation newOperation = new Operation();
              newOperation.setWorksheet(worksheet);
              newOperation.setTableName(dto.getTableName());
              newOperation.setColumnName(dto.getColumnName());
              newOperation.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
              return newOperation;
            }
        );

    if (operation.getSuppression() != null) {
      return ApiResponse.ofError("This column already uses suppression.");
    }

    Suppression suppression = new Suppression();
    suppression.setSuppressionToken(dto.getSuppressionToken());
    operation.setSuppression(suppression);
    operationRepository.save(operation);
    suppressionRepository.save(suppression);

    return ApiResponse.ofSuccess("Added suppression operation successfully");
  }
}
