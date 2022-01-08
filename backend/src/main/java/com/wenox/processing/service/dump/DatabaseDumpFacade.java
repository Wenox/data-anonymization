package com.wenox.processing.service.dump;

public interface DatabaseDumpFacade {

  void dumpToArchive(String databaseName, String compressedArchivePath);

  void dumpToScript(String databaseName, String scriptFilePath);
}
