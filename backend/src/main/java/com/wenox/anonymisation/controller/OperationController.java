package com.wenox.anonymisation.controller;

import com.wenox.anonymisation.dto.operations.AddOperationRequest;
import com.wenox.anonymisation.dto.operations.ColumnOperations;
import com.wenox.anonymisation.dto.operations.ColumnOperationsForTableResponse;
import com.wenox.anonymisation.service.OperationService;
import com.wenox.users.dto.ApiResponse;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {

  private final OperationService operationService;

  public OperationController(OperationService operationService) {
    this.operationService = operationService;
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @GetMapping("/api/v1/worksheet/{id}/operations")
  public ResponseEntity<ColumnOperationsForTableResponse> getColumnOperationsForTableInWorksheet(
      @PathVariable("id") String id,
      @RequestParam("table") String table,
      Authentication auth) {
    return ResponseEntity.ok(operationService.getOperationsForWorksheet(id, table, auth));
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @PutMapping("/api/v1/worksheet/{id}/operations")
  public ResponseEntity<ApiResponse> addOperationForColumn(
      @PathVariable("id") String id,
      @Valid @RequestBody AddOperationRequest dto,
      Authentication auth) {
    return ResponseEntity.ok(operationService.addOperationForColumn(id, dto, auth));
  }
}
