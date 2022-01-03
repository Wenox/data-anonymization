package com.wenox.anonymisation.controller;

import com.wenox.anonymisation.domain.Worksheet;
import com.wenox.anonymisation.dto.CreateWorksheetRequest;
import com.wenox.anonymisation.dto.WorksheetCreatedResponse;
import com.wenox.anonymisation.dto.WorksheetSummaryResponse;
import com.wenox.anonymisation.service.WorksheetService;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.jdbc.Work;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/worksheets")
public class WorksheetController {

  private final WorksheetService worksheetService;

  public WorksheetController(WorksheetService worksheetService) {
    this.worksheetService = worksheetService;
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<List<Worksheet>> getAllMyWorksheets() {
    return ResponseEntity.ok(worksheetService.getAllMyWorksheets());
  }

  @GetMapping("/me/{id}/summary")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<WorksheetSummaryResponse> getWorksheetSummary(@PathVariable("id") String id, Authentication auth) {
    return ResponseEntity.ok(WorksheetSummaryResponse.from(worksheetService.getMyWorksheetSummary(id, auth)));
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<WorksheetCreatedResponse> createMyWorksheet(@Valid @RequestBody CreateWorksheetRequest dto,
                                                                    Authentication auth) {
    return ResponseEntity.ok(WorksheetCreatedResponse.from(worksheetService.createMyWorksheet(dto, auth)));
  }
}
