package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.domain.FileType;
import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.uploader.core.event.TemplateCreatedEvent;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TemplateService {

  private final TemplateRepository templateRepository;
  private final ApplicationEventPublisher publisher;
  private final AuthService authService;

  public TemplateService(TemplateRepository templateRepository,
                         ApplicationEventPublisher publisher,
                         AuthService authService) {
    this.templateRepository = templateRepository;
    this.publisher = publisher;
    this.authService = authService;
  }

  public UUID createFrom(FileDTO fileDTO, FileType type, String title, Authentication auth) {
    final var me = authService.getMe(auth);

    final var template = new Template();
    template.setTitle(title);
    template.setStatus(TemplateStatus.NEW);
    template.setType(type);
    template.setAuthor(me.getEmail());
    template.setDescription(me.getPurpose());
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
