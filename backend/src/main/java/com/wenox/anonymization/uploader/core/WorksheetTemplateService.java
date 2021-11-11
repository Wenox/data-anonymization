package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WorksheetTemplateService {

  private final WorksheetTemplateRepository worksheetTemplateRepository;
  private final FileUploader fileUploader;

  public WorksheetTemplateService(WorksheetTemplateRepository worksheetTemplateRepository, FileUploader fileUploader) {
    this.worksheetTemplateRepository = worksheetTemplateRepository;
    this.fileUploader = fileUploader;
  }

  public WorksheetTemplate createFrom(final MultipartFile templateFile, final FileType type) {
    final var worksheetTemplate = new WorksheetTemplate();
    worksheetTemplate.setType(type);
    worksheetTemplate.setAuthor("Principal");
    worksheetTemplate.setDescription("Description");
    worksheetTemplate.setMetadataFile(null);
    worksheetTemplate.setCreatedDate(LocalDateTime.now());
    worksheetTemplate.setDatabaseName("dbname_" + new Random().nextInt(Integer.MAX_VALUE));

    try {
      final var savedTemplateFile = fileUploader.upload(templateFile, type);
      worksheetTemplate.setStatus("TEMPLATE_UPLOAD_SUCCESS"); // todo: enum
      worksheetTemplate.setTemplateFile(savedTemplateFile);
    } catch (final IOException ex) {
      worksheetTemplate.setStatus("TEMPLATE_UPLOAD_FAILURE");
    }

    return worksheetTemplateRepository.save(worksheetTemplate);
  }

  public String getStatus(final UUID uuid) {
    return worksheetTemplateRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getStatus();
  }
}
