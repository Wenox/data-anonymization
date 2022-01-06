package com.wenox.processing.controller;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.service.OutcomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/outcomes")
public class OutcomeController {

  private final OutcomeService outcomeService;

  public OutcomeController(OutcomeService outcomeService) {
    this.outcomeService = outcomeService;
  }

  @PostMapping("/generate")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<String> generateOutcome(@RequestParam("worksheet_id") String worksheetId, Authentication auth) {
    final String id = outcomeService.generateOutcome(worksheetId, auth);
    return ResponseEntity.accepted().body(id);
  }
}
