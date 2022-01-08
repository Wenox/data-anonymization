package com.wenox.processing.controller;

import com.wenox.processing.domain.Outcome;
import com.wenox.processing.dto.GenerateOutcomeRequest;
import com.wenox.processing.dto.OutcomeResponse;
import com.wenox.processing.service.OutcomeService;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/api/v1/outcomes")
public class OutcomeController {

  private final OutcomeService outcomeService;

  public OutcomeController(OutcomeService outcomeService) {
    this.outcomeService = outcomeService;
  }

  @PostMapping("/generate")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<String> generateOutcome(@Valid @RequestBody GenerateOutcomeRequest dto, Authentication auth) {
    final String id = outcomeService.generateOutcome(dto, auth);
    return ResponseEntity.accepted().body(id);
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<List<OutcomeResponse>> getMyOutcomes(Authentication auth) {
    return ResponseEntity.ok(outcomeService.getMyOutcomes(auth).stream().map(OutcomeResponse::from).toList());
  }

  @GetMapping("/{id}/")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<byte[]> downloadOutcomeDump(@PathVariable("id") String id, Authentication auth) throws IOException {
    return ResponseEntity.ok(outcomeService.downloadOutcomeDump(id, auth));
  }
}
