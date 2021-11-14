package com.wenox.anonymization.uploader.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TemplateFileStorage implements FileStorage {

  public void store(FileData fileData) throws IOException {
    TemplateFileData templateFileData = (TemplateFileData) fileData;

    final var directory = Files.createDirectories(Path.of("E:/anon/data-anonymization/stored_files"));
    final var multipartFile = templateFileData.getMultipartFile();
    Files.copy(
        multipartFile.getInputStream(),
        directory.resolve(templateFileData.getSavedFileName()),
        StandardCopyOption.REPLACE_EXISTING
    );
  }
}
