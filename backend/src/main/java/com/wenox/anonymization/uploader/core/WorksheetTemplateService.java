package com.wenox.anonymization.uploader.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.WorksheetTemplateCreatedEvent;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WorksheetTemplateService {

  private final WorksheetTemplateRepository worksheetTemplateRepository;
  private final ApplicationEventPublisher publisher;

  public WorksheetTemplateService(WorksheetTemplateRepository worksheetTemplateRepository,
                                  ApplicationEventPublisher publisher) {
    this.worksheetTemplateRepository = worksheetTemplateRepository;
    this.publisher = publisher;
  }

  public UUID createFrom(FileDTO fileDTO, FileType type) {
    final var worksheetTemplate = new WorksheetTemplate();
    worksheetTemplate.setType(type);
    worksheetTemplate.setAuthor("Principal");
    worksheetTemplate.setDescription("Description");
    worksheetTemplate.setMetadataFile(null);
    worksheetTemplate.setTemplateFile(null);
    worksheetTemplate.setCreatedDate(LocalDateTime.now());
    worksheetTemplate.setDatabaseName("dbname_" + new Random().nextInt(Integer.MAX_VALUE));
    worksheetTemplateRepository.save(worksheetTemplate);

    publisher.publishEvent(new WorksheetTemplateCreatedEvent(worksheetTemplate, fileDTO));

    return worksheetTemplate.getUuid();
  }

  public String getStatus(UUID uuid) {
    return worksheetTemplateRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getStatus();
  }

  public WorksheetTemplateMetadata getMetadata(UUID uuid) throws IOException {
    final FileEntity metadataFile = worksheetTemplateRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getMetadataFile();

    return new ObjectMapper().readValue(
        new File("E:/anon/data-anonymization/stored_files/" + metadataFile.getSavedName()),
        WorksheetTemplateMetadata.class
    );
  }
}
