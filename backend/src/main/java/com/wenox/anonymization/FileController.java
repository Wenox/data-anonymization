package com.wenox.anonymization;

import java.io.IOException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

  private final FileUploader fileUploader;

  public FileController(final FileUploader fileUploader) {
    this.fileUploader = fileUploader;
  }

  @PostMapping("/api/v1/files")
  public void upload(@RequestParam("file") MultipartFile file, @RequestParam("type") FileType type) {
    System.out.println("Inside /api/v1/files");
    System.out.println("Type: " + type);

    System.out.println(file);

    System.out.println("file name: " + file.getName());
    System.out.println("file original name: " + file.getOriginalFilename());
    System.out.println("file content type: " + file.getContentType());
    System.out.println("file size: " + file.getSize());

    try {
      fileUploader.upload(file, type);
    } catch (final IOException ex) {
      ex.printStackTrace();
    }

    System.out.println("All successful");
  }
}
