package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.WorksheetTemplateCreatedEvent;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class WorksheetTemplateController {

  private final ApplicationEventPublisher publisher;
  private final WorksheetTemplateService worksheetTemplateService;

  public WorksheetTemplateController(final ApplicationEventPublisher publisher, final WorksheetTemplateService worksheetTemplateService) {
    this.publisher = publisher;
    this.worksheetTemplateService = worksheetTemplateService;
  }

  @PostMapping("/api/v1/worksheet-templates")
  public ResponseEntity<UUID> create(@RequestParam("file") MultipartFile worksheetTemplateFile,
                                     @RequestParam("type") FileType type) {
    final var worksheetTemplate = worksheetTemplateService.createFrom(worksheetTemplateFile, type);
    publisher.publishEvent(new WorksheetTemplateCreatedEvent(worksheetTemplate));
    return ResponseEntity.ok(worksheetTemplate.getUuid());
  }
}
