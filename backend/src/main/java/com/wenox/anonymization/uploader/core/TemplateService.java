package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.TemplateCreatedEvent;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TemplateService {

  private final TemplateRepository templateRepository;
  private final ApplicationEventPublisher publisher;

  public TemplateService(TemplateRepository templateRepository,
                         ApplicationEventPublisher publisher) {
    this.templateRepository = templateRepository;
    this.publisher = publisher;
  }

  public UUID createFrom(FileDTO fileDTO, FileType type) {
    final var template = new Template();
    template.setStatus(TemplateStatus.NEW);
    template.setType(type);
    template.setAuthor("Principal");
    template.setDescription("Description");
    template.setMetadata(null);
    template.setTemplateFile(null);
    template.setCreatedDate(LocalDateTime.now());
    template.setDatabaseName("dbname_" + new Random().nextInt(Integer.MAX_VALUE));
    templateRepository.save(template);

    publisher.publishEvent(new TemplateCreatedEvent(template, fileDTO));

    return template.getUuid();
  }

  public TemplateStatus getStatus(UUID uuid) {
    return templateRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getStatus();
  }

  public TemplateMetadata getMetadata(UUID uuid) {
    return templateRepository.findById(uuid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getMetadata();
  }
}
