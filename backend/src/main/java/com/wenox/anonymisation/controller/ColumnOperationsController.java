package com.wenox.anonymisation.controller;

import com.wenox.anonymisation.dto.columnoperations.AddColumnShuffleRequest;
import com.wenox.anonymisation.dto.columnoperations.AddRowShuffleRequest;
import com.wenox.anonymisation.dto.columnoperations.AddSuppressionRequest;
import com.wenox.anonymisation.service.ColumnOperationsService;
import com.wenox.users.dto.ApiResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColumnOperationsController {

  private final ColumnOperationsService columnOperationsService;

  public ColumnOperationsController(ColumnOperationsService columnOperationsService) {
    this.columnOperationsService = columnOperationsService;
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @PutMapping("/api/v1/worksheet/{id}/column-operations/add-suppression")
  public ResponseEntity<ApiResponse> addSuppressionOperationForColumn(@PathVariable("id") String id,
                                                                      @Valid @RequestBody AddSuppressionRequest dto,
                                                                      Authentication auth) {
    return ResponseEntity.ok(columnOperationsService.addSuppressionOperationForColumn(id, dto, auth));
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @PutMapping("/api/v1/worksheet/{id}/column-operations/add-column-shuffle")
  public ResponseEntity<ApiResponse> addColumnShuffleOperationForColumn(@PathVariable("id") String id,
                                                                        @Valid @RequestBody AddColumnShuffleRequest dto,
                                                                        Authentication auth) {
    return ResponseEntity.ok(columnOperationsService.addColumnShuffleOperationForColumn(id, dto, auth));
  }

  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  @PutMapping("/api/v1/worksheet/{id}/column-operations/add-row-shuffle")
  public ResponseEntity<ApiResponse> addRowShuffleOperationForColumn(@PathVariable("id") String id,
                                                                     @Valid @RequestBody AddRowShuffleRequest dto,
                                                                     Authentication auth) {
    return ResponseEntity.ok(columnOperationsService.addRowShuffleOperationForColumn(id, dto, auth));
  }
}
