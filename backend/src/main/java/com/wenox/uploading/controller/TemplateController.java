package com.wenox.uploading.controller;

import com.wenox.uploading.domain.metadata.TemplateMetadata;
import com.wenox.uploading.dto.CreateTemplateDto;
import com.wenox.uploading.dto.MyTemplateDto;
import com.wenox.uploading.service.TemplateService;
import com.wenox.uploading.domain.template.TemplateStatus;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

  private final TemplateService templateService;

  public TemplateController(TemplateService templateService) {
    this.templateService = templateService;
  }

  @PostMapping
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<String> createTemplate(@Valid CreateTemplateDto createTemplateDto, Authentication auth) throws IOException {
    final String id = templateService.createTemplate(createTemplateDto, auth);
    return ResponseEntity.accepted().body(id);
  }

  @GetMapping("/{id}/dump")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<byte[]> downloadTemplateDump(@PathVariable("id") String id, Authentication auth) throws IOException {
    return ResponseEntity.ok(templateService.downloadTemplateDump(id, auth));
  }

  @GetMapping("/{id}/status")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<TemplateStatus> getStatus(@PathVariable("id") String id) {
    return ResponseEntity.ok(templateService.getStatus(id));
  }

  @GetMapping("/{id}/metadata")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<TemplateMetadata> getMetadata(@PathVariable("id") String id) {
    return ResponseEntity.ok(templateService.getMetadata(id));
  }

  @GetMapping("/me")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<List<MyTemplateDto>> getAllMyTemplates(Authentication auth) {
    return ResponseEntity.ok(templateService.getAllMyTemplates(auth).stream().map(MyTemplateDto::from).collect(
        Collectors.toList()));
  }

  @GetMapping("/me/valid")
  @PreAuthorize("hasAnyAuthority('VERIFIED_USER', 'ADMIN')")
  public ResponseEntity<List<MyTemplateDto>> getAllMyValidTemplates(Authentication auth) {
    return ResponseEntity.ok(templateService.getAllMyTemplates(auth).stream().map(MyTemplateDto::from).collect(Collectors.toList()));
  }
}
