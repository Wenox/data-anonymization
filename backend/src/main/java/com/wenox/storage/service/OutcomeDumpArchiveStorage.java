package com.wenox.storage.service;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OutcomeDumpArchiveStorage extends LocalFileStorage {

  @Value("${processing.dumps.archives.path}")
  private String outcomeArchivesPath;

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(outcomeArchivesPath);
  }
}
