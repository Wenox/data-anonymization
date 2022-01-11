package com.wenox.storage.service.outcome;

import com.wenox.storage.service.LocalFileStorage;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AnonymisationScriptStorage extends LocalFileStorage {

  private final String anonymisationScriptsPath;

  public AnonymisationScriptStorage(@Value("${processing.anonymisations.scripts.path}") String anonymisationScriptsPath) {
    this.anonymisationScriptsPath = anonymisationScriptsPath;
  }

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(anonymisationScriptsPath);
  }
}
