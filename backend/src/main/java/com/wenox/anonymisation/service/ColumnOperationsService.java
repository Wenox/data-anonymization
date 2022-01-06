package com.wenox.anonymisation.service;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Suppression;
import com.wenox.anonymisation.dto.columnoperations.AddSuppressionRequest;
import com.wenox.anonymisation.repository.ColumnOperationsRepository;
import com.wenox.anonymisation.repository.SuppressionRepository;
import com.wenox.anonymisation.repository.WorksheetRepository;
import com.wenox.users.dto.ApiResponse;
import com.wenox.users.service.AuthService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ColumnOperationsService {

  private final AuthService authService;
  private final WorksheetRepository worksheetRepository;
  private final ColumnOperationsRepository columnOperationsRepository;
  private final SuppressionRepository suppressionRepository;

  public ColumnOperationsService(AuthService authService, WorksheetRepository worksheetRepository,
                                 ColumnOperationsRepository columnOperationsRepository,
                                 SuppressionRepository suppressionRepository) {
    this.authService = authService;
    this.worksheetRepository = worksheetRepository;
    this.columnOperationsRepository = columnOperationsRepository;
    this.suppressionRepository = suppressionRepository;
  }

  @Transactional
  public ApiResponse addSuppressionOperationForColumn(String id, AddSuppressionRequest dto, Authentication auth) {
    final var me = authService.getMe(auth);
    final var worksheet = worksheetRepository.findById(id).orElseThrow();
    if (!me.getId().equals(worksheet.getUser().getId())) {
      throw new RuntimeException("The worksheet does not belong to this user.");
    }

    var columnOperations = columnOperationsRepository.findOperationsForColumn(worksheet, dto.getTableName(), dto.getColumnName())
        .orElseGet(
            () -> {
              var newColumnOperations = new ColumnOperations();
              newColumnOperations.setWorksheet(worksheet);
              newColumnOperations.setTableName(dto.getTableName());
              newColumnOperations.setColumnName(dto.getColumnName());
              newColumnOperations.setPrimaryKeyColumnName(dto.getPrimaryKeyColumnName());
              return newColumnOperations;
            }
        );

    if (columnOperations.getSuppression() != null) {
      return ApiResponse.ofError("This column already uses suppression.");
    }

    Suppression suppression = new Suppression();
    suppression.setSuppressionToken(dto.getSuppressionToken());
    columnOperations.setSuppression(suppression);
    columnOperationsRepository.save(columnOperations);
    suppressionRepository.save(suppression);

    return ApiResponse.ofSuccess("Suppression added successfully.");
  }
}
