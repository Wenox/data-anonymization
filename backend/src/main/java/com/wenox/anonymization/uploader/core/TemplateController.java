package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.io.IOException;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

  private final TemplateService templateService;

  public TemplateController(TemplateService templateService) {
    this.templateService = templateService;
  }

  @PostMapping
  public ResponseEntity<UUID> create(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("type") FileType type) throws IOException {
    final UUID uuid = templateService.createFrom(FileDTO.from(multipartFile), type);
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
