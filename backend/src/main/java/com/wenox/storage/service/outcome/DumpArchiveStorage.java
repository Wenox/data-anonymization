package com.wenox.storage.service.outcome;

import com.wenox.storage.service.LocalFileStorage;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DumpArchiveStorage extends LocalFileStorage {

  private final String outcomeArchivesPath;

  public DumpArchiveStorage(@Value("${processing.dumps.archives.path}") String outcomeArchivesPath) {
    this.outcomeArchivesPath = outcomeArchivesPath;
  }

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(outcomeArchivesPath);
  }
}
