package com.wenox.storage.service.outcome;

import com.wenox.storage.service.FileStorage;
import org.springframework.stereotype.Component;

@Component
public class OutcomeDumpStorageFactory {

  private final FileStorage archiveStorage;
  private final FileStorage scriptStorage;

  public OutcomeDumpStorageFactory(OutcomeDumpArchiveStorage archiveStorage,
                                   OutcomeDumpScriptStorage scriptStorage) {
    this.archiveStorage = archiveStorage;
    this.scriptStorage = scriptStorage;
  }

  public FileStorage getArchiveStorage() {
    return archiveStorage;
  }

  public FileStorage getScriptStorage() {
    return scriptStorage;
  }
}
