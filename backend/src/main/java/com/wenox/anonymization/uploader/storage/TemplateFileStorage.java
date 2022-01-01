package com.wenox.anonymization.uploader.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class TemplateFileStorage implements FileStorage {

  @Value("${uploader.templates.path}")
  private String templatesPath;

  public void store(FileData fileData) throws IOException {
    TemplateFileData templateFileData = (TemplateFileData) fileData;

    final var directory = Files.createDirectories(Path.of(templatesPath));
    final var fileDTO = templateFileData.getFileDTO();

    FileCopyUtils.copy(fileDTO.getBytes(), directory.resolve(templateFileData.getSavedFileName()).toFile());
  }
}
