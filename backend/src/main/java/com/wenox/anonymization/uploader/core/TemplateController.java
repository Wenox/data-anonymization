package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.io.IOException;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

  private final TemplateService templateService;

  public TemplateController(TemplateService templateService) {
    this.templateService = templateService;
  }

  @PostMapping
  public ResponseEntity<UUID> createTemplate(@Valid TemplateDto templateDto, Authentication auth) throws IOException {
    final UUID uuid = templateService.createTemplate(templateDto, auth);
    return ResponseEntity.accepted().body(uuid);
  }

  @GetMapping("/{uuid}/status")
  public ResponseEntity<TemplateStatus> getStatus(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(templateService.getStatus(uuid));
  }

  @GetMapping("/{uuid}/metadata")
  public ResponseEntity<TemplateMetadata> getMetadata(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(templateService.getMetadata(uuid));
  }
}
