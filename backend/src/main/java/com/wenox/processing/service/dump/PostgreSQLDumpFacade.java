package com.wenox.processing.service.dump;

import com.wenox.processing.service.mirror.DatabaseHostDetails;
import com.wenox.processing.service.mirror.PostgreSQLHostDetails;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDumpFacade implements DatabaseDumpFacade {

  private final DatabaseDumpToArchiveService dumpToArchiveService;
  private final DatabaseDumpToScriptService dumpToScriptService;
  private final DatabaseHostDetails hostDetails;

  public PostgreSQLDumpFacade(PostgreSQLDumpToArchiveService postgreSQLDumpToArchiveService,
                              PostgreSQLDumpToScriptService postgreSQLDumpToScriptService,
                              PostgreSQLHostDetails postgreSQLHostDetails) {
    this.dumpToArchiveService = postgreSQLDumpToArchiveService;
    this.dumpToScriptService = postgreSQLDumpToScriptService;
    this.hostDetails = postgreSQLHostDetails;
  }

  @Override
  public void dumpToArchive(String databaseName, String compressedArchivePath) {
    dumpToArchiveService.dump(hostDetails, databaseName, compressedArchivePath);
  }

  @Override
  public void dumpToScript(String databaseName, String scriptFilePath) {
    dumpToScriptService.dump(hostDetails, databaseName, scriptFilePath);
  }
}
