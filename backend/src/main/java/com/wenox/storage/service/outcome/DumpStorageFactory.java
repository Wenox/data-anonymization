package com.wenox.storage.service.outcome;

import com.wenox.storage.service.FileStorage;
import org.springframework.stereotype.Component;

@Component
public class DumpStorageFactory {

  private final FileStorage dumpArchiveStorage;
  private final FileStorage dumpScriptStorage;
  private final FileStorage anonymisationScriptStorage;

  public DumpStorageFactory(DumpArchiveStorage dumpArchiveStorage,
                            DumpScriptStorage dumpScriptStorage,
                            AnonymisationScriptStorage anonymisationScriptStorage) {
    this.dumpArchiveStorage = dumpArchiveStorage;
    this.dumpScriptStorage = dumpScriptStorage;
    this.anonymisationScriptStorage = anonymisationScriptStorage;
  }

  public FileStorage getDumpArchiveStorage() {
    return dumpArchiveStorage;
  }

  public FileStorage getDumpScriptStorage() {
    return dumpScriptStorage;
  }

  public FileStorage getAnonymisationScriptStorage() {
    return anonymisationScriptStorage;
  }
}
