package com.wenox.storage.service.outcome;

import com.wenox.storage.service.LocalFileStorage;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnonymisationScriptStorage extends LocalFileStorage {

  @Value("${processing.anonymisations.scripts.path}")
  private String anonymisationScriptsPath;

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(anonymisationScriptsPath);
  }
}
