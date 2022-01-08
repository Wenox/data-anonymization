package com.wenox.processing.service.dump;

public interface DatabaseDumpFacade {

  void dumpToArchive(String databaseName, String archiveName);

  void dumpToScript(String databaseName, String scriptName);
}
