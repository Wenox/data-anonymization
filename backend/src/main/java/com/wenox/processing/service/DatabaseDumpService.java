package com.wenox.processing.service;

import com.wenox.processing.service.mirror.DatabaseHostDetails;

public interface DatabaseDumpService {

  void dumpDatabaseToScriptFile(DatabaseHostDetails details, String databaseName, String scriptFilePath);
  void dumpDatabaseToCompressedArchive(DatabaseHostDetails details, String databaseName, String compressedArchivePath);
}
