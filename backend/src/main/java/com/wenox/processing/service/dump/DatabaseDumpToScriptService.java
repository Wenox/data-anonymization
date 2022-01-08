package com.wenox.processing.service.dump;

import com.wenox.processing.service.mirror.DatabaseHostDetails;

public interface DatabaseDumpToScriptService {

  void dump(DatabaseHostDetails details, String databaseName, String scriptFilePath);
}
