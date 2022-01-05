package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.Operation;
import com.wenox.anonymisation.dto.ColumnOperationsResponse;
import com.wenox.anonymisation.dto.OperationResponse;
import com.wenox.anonymisation.repository.OperationRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.users.service.AuthService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

  public List<ColumnOperationsResponse> getOperationsForWorksheet(String id, String table, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }
    final var metadata = worksheet.getTemplate().getMetadata();

    final List<Column> columns = metadata.getColumnsByTable(table);
    columns.forEach(c -> System.out.println(c.getColumnName()));

    final List<Operation> operations = operationRepository.findAllByWorksheetAndTableName(worksheet, table);


    final Map<String, List<Operation>> operationsByColumn =
        operations.stream().collect(Collectors.groupingBy(Operation::getColumnName));


    List<ColumnOperationsResponse> dto = new ArrayList<>();
    for (var column : columns) {
      var item = new ColumnOperationsResponse();
      item.setColumn(column);
      item.setOperations(operationsByColumn
          .getOrDefault(column.getColumnName(), List.of())
          .stream()
          .map(OperationResponse::from)
          .toList());
      dto.add(item);
    }

    return dto;
  }
}
