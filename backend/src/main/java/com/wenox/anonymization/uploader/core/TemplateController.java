package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<String> createTemplate(@Valid CreateTemplateDto createTemplateDto, Authentication auth) throws IOException {
    final String id = templateService.createTemplate(createTemplateDto, auth);
    return ResponseEntity.accepted().body(id);
  }

  @GetMapping("/{uuid}/status")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<TemplateStatus> getStatus(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(templateService.getStatus(uuid));
  }

  @GetMapping("/{uuid}/metadata")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<TemplateMetadata> getMetadata(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(templateService.getMetadata(uuid));
  }

  @GetMapping
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<List<MyTemplateDto>> getAllMyTemplates(Authentication auth) {
    return ResponseEntity.ok(templateService.getAllMyTemplates(auth).stream().map(MyTemplateDto::from).toList());
  }
}
