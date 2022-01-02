package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.core.service.AuthService;
import com.wenox.anonymization.uploader.core.event.TemplateCreatedEvent;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
import com.wenox.anonymization.uploader.storage.FileStorage;
import com.wenox.anonymization.uploader.storage.TemplateFileStorage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TemplateService {

  private final TemplateRepository templateRepository;
  private final ApplicationEventPublisher publisher;
  private final AuthService authService;
  private final FileStorage fileStorage;

  public TemplateService(TemplateRepository templateRepository,
                         ApplicationEventPublisher publisher,
                         AuthService authService,
                         TemplateFileStorage fileStorage) {
    this.templateRepository = templateRepository;
    this.publisher = publisher;
    this.authService = authService;
    this.fileStorage = fileStorage;
  }

  public String createTemplate(CreateTemplateDto dto, Authentication auth) throws IOException {
    final var me = authService.getMe(auth);

    var fileDto = FileDto.from(dto.getFile());

    final var template = new Template();
    template.setUser(me);
    template.setDatabaseName("db-" + UUID.randomUUID());
    template.setTitle(dto.getTitle());
    template.setType(dto.getType());
    template.setDescription(dto.getDescription());
    template.setStatus(TemplateStatus.NEW);
    template.setMetadata(null);
    template.setTemplateFile(null);
    template.setCreatedDate(LocalDateTime.now());
    templateRepository.save(template);

    publisher.publishEvent(new TemplateCreatedEvent(template, fileDto));

    return template.getId();
  }

  public TemplateStatus getStatus(String id) {
    return templateRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getStatus();
  }

  public TemplateMetadata getMetadata(String id) {
    return templateRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
        .getMetadata();
  }

  public List<Template> getAllMyTemplates(Authentication auth) {
    final var me = authService.getMe(auth);
    return me.getTemplates();
  }

  public byte[] downloadDump(String id) throws IOException {
    var template = templateRepository.findById(id).orElseThrow();
    var dump = template.getTemplateFile();
    if (dump == null) {
      throw new RuntimeException("No dump file associated with this template!");
    }
    var savedFileName = dump.getSavedFileName();
    return fileStorage.retrieve(savedFileName);
  }
}
