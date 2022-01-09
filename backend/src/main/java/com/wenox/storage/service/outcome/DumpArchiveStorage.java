package com.wenox.storage.service.outcome;

import com.wenox.storage.service.LocalFileStorage;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DumpArchiveStorage extends LocalFileStorage {

  @Value("${processing.dumps.archives.path}")
  private String outcomeArchivesPath;

  @Override
  public Path getFileDirectoryPath() {
    return Path.of(outcomeArchivesPath);
  }
}
