package com.wenox.storage.service;

import com.wenox.storage.domain.FileData;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.util.FileCopyUtils;

abstract public class LocalFileStorage implements FileStorage {

  abstract public Path getFileDirectoryPath();

  @Override
  public byte[] retrieve(String savedFileName) throws IOException {
    Path directory = getFileDirectoryPath();
    Path pathToFile = directory.resolve(savedFileName);
    try (InputStream reader = Files.newInputStream(pathToFile)) {
      return reader.readAllBytes();
    }
  }

  @Override
  public void save(FileData fileData) throws IOException {
    Path directory = Files.createDirectories(getFileDirectoryPath());
    Path pathToFile = directory.resolve(fileData.getSavedFileName());
    FileCopyUtils.copy(fileData.getBytes(), pathToFile.toFile());
  }
}
