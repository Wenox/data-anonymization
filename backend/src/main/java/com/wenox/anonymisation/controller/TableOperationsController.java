package com.wenox.anonymisation.controller;

import com.wenox.anonymisation.dto.tableoperations.TableOperationsResponse;
import com.wenox.anonymisation.service.TableOperationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TableOperationsController {

  private final TableOperationsService tableOperationsService;

  public TableOperationsController(TableOperationsService tableOperationsService) {
    this.tableOperationsService = tableOperationsService;
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @GetMapping("/api/v1/worksheet/{id}/table-operations/{table}")
  public ResponseEntity<TableOperationsResponse> getTableOperations(@PathVariable("id") String id,
                                                                    @PathVariable("table") String table,
                                                                    Authentication auth) {
    return ResponseEntity.ok(tableOperationsService.getOperationsForWorksheet(id, table, auth));
  }
}
