package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;
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
@RequestMapping("/api/v1/worksheet-templates")
public class WorksheetTemplateController {

  private final WorksheetTemplateService worksheetTemplateService;

  public WorksheetTemplateController(WorksheetTemplateService worksheetTemplateService) {
    this.worksheetTemplateService = worksheetTemplateService;
  }

  @PostMapping
  public ResponseEntity<UUID> create(@RequestParam("file") MultipartFile multipartFile,
                                     @RequestParam("type") FileType type) throws IOException {
    final UUID uuid = worksheetTemplateService.createFrom(FileDTO.from(multipartFile), type);
    return ResponseEntity.accepted().body(uuid);
  }

  @GetMapping("/{uuid}/status")
  public ResponseEntity<WorksheetTemplateStatus> getStatus(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(worksheetTemplateService.getStatus(uuid));
  }

  @GetMapping("/{uuid}/metadata")
  public ResponseEntity<WorksheetTemplateMetadata> getMetadata(@PathVariable("uuid") UUID uuid) {
    return ResponseEntity.ok(worksheetTemplateService.getMetadata(uuid));
  }
}
