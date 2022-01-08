package com.wenox.processing.service.dump;

import com.wenox.processing.service.mirror.DatabaseHostDetails;

public interface DatabaseDumpToArchiveService {

  void dump(DatabaseHostDetails details, String databaseName, String compressedArchivePath);
}
