package com.wenox.anonymization.uploader.core;

import com.wenox.anonymization.commons.domain.FileType;
import com.wenox.anonymization.uploader.core.event.FileUploadedFailureEvent;
import com.wenox.anonymization.uploader.core.event.FileUploadedSuccessEvent;
import java.io.IOException;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

  private final FileUploader fileUploader;
  private final ApplicationEventPublisher publisher;

  public FileController(final FileUploader fileUploader, final ApplicationEventPublisher publisher) {
    this.fileUploader = fileUploader;
    this.publisher = publisher;
  }

  @PostMapping("/api/v1/files")
  public void upload(@RequestParam("file") MultipartFile file, @RequestParam("type") FileType type) {
    try {
      final var savedFile = fileUploader.upload(file, type);
      publisher.publishEvent(new FileUploadedSuccessEvent(savedFile));
    } catch (final IOException ex) {
      ex.printStackTrace();
      publisher.publishEvent(new FileUploadedFailureEvent(ex));
    }
  }
}
