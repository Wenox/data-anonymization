package com.wenox.uploading.template.service;

import com.wenox.storage.domain.FileData;
import com.wenox.storage.service.FileStorage;
import com.wenox.uploading.template.domain.TemplateStatus;
import com.wenox.uploading.template.domain.Template;
import com.wenox.uploading.template.dto.CreateTemplateDto;
import com.wenox.uploading.template.repository.TemplateRepository;
import com.wenox.users.service.AuthService;
import com.wenox.uploading.template.event.TemplateCreatedEvent;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import com.wenox.storage.service.template.TemplateDumpStorage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
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
  private final FileStorage fileStorage;

  public TemplateService(TemplateRepository templateRepository,
                         ApplicationEventPublisher publisher,
                         AuthService authService,
                         TemplateDumpStorage templateDumpStorage) {
    this.templateRepository = templateRepository;
    this.publisher = publisher;
    this.authService = authService;
    this.fileStorage = templateDumpStorage;
  }

  public String createTemplate(CreateTemplateDto dto, Authentication auth) throws IOException {
    final var me = authService.getMe(auth);

    var fileData = FileData.from(dto.getFile());

    final var template = new Template();
    template.setUser(me);
    template.setTemplateDatabaseName("db-" + UUID.randomUUID());
    template.setTitle(dto.getTitle());
    template.setType(dto.getType());
    template.setRestoreMode(dto.getRestoreMode());
    template.setDescription(dto.getDescription());
    template.setStatus(TemplateStatus.NEW);
    template.setMetadata(null);
    template.setTemplateFile(null);
    template.setCreatedDate(LocalDateTime.now());
    templateRepository.save(template);

    publisher.publishEvent(new TemplateCreatedEvent(template, fileData));

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

  public byte[] downloadTemplateDump(String id, Authentication auth) throws IOException {
    var me = authService.getMe(auth);
    var template = templateRepository.findById(id).orElseThrow();
    if (!me.equals(template.getUser())) {
      throw new RuntimeException("This template belongs to other user!");
    }

    var dump = template.getTemplateFile();
    if (dump == null) {
      throw new RuntimeException("No dump file associated with this template!");
    }

    var savedFileName = dump.getSavedFileName();
    return fileStorage.retrieve(savedFileName);
  }
}
