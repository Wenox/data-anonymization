package com.wenox.anonymization;

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

  static class FileUploadedSuccessEvent {

    private final FileEntity file;

    public FileUploadedSuccessEvent(FileEntity file) {
      this.file = file;
    }

    public FileEntity getFile() {
      return file;
    }
  }

  static class FileUploadedFailureEvent {

    private final IOException exception;

    public FileUploadedFailureEvent(final IOException exception) {
      this.exception = exception;
    }

    public IOException getException() {
      return exception;
    }
  }
}
