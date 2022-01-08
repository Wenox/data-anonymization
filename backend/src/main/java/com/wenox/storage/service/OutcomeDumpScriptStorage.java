package com.wenox.storage.service;

import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OutcomeDumpScriptStorage extends LocalFileStorage {

  @Value("${processing.dumps.scripts.path}")
  private String outcomeScriptsPath;

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(outcomeScriptsPath);
  }
}
