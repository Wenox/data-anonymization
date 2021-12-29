package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.uploader.core.event.TemplateCreatedEvent;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import java.io.IOException;
import java.time.LocalDateTime;
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

  public UUID createTemplate(TemplateDto templateDto, Authentication auth) throws IOException {
    final var me = authService.getMe(auth);

    var fileDto = FileDto.from(templateDto.getFile());

    final var template = new Template();
    template.setTitle(templateDto.getTitle());
    template.setType(templateDto.getType());
    template.setStatus(TemplateStatus.NEW);
    template.setAuthor(me.getEmail());
    template.setDescription(me.getPurpose());
    template.setMetadata(null);
    template.setTemplateFile(null);
    template.setCreatedDate(LocalDateTime.now());
    template.setDatabaseName("db-" + UUID.randomUUID());
    templateRepository.save(template);

    publisher.publishEvent(new TemplateCreatedEvent(template, fileDto));

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
