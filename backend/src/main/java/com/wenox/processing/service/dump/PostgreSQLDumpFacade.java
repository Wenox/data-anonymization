package com.wenox.processing.service.dump;

import com.wenox.processing.service.mirror.DatabaseHostDetails;
import com.wenox.processing.service.mirror.PostgreSQLHostDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostgreSQLDumpFacade implements DatabaseDumpFacade {

  private final DatabaseDumpToArchiveService dumpToArchiveService;
  private final DatabaseDumpToScriptService dumpToScriptService;
  private final DatabaseHostDetails hostDetails;
  private final String archivesPath;
  private final String scriptsPath;

  public PostgreSQLDumpFacade(PostgreSQLDumpToArchiveService postgreSQLDumpToArchiveService,
                              PostgreSQLDumpToScriptService postgreSQLDumpToScriptService,
                              PostgreSQLHostDetails postgreSQLHostDetails,
                              @Value("${processing.dumps.archives.path}") String archivesPath,
                              @Value("${processing.dumps.scripts.path}") String scriptsPath) {
    this.dumpToArchiveService = postgreSQLDumpToArchiveService;
    this.dumpToScriptService = postgreSQLDumpToScriptService;
    this.hostDetails = postgreSQLHostDetails;
    this.archivesPath = archivesPath;
    this.scriptsPath = scriptsPath;
  }

  @Override
  public void dumpToArchive(String databaseName, String archiveName) {
    dumpToArchiveService.dump(hostDetails, databaseName, archivesPath + "/" + archiveName);
  }

  @Override
  public void dumpToScript(String databaseName, String scriptName) {
    dumpToScriptService.dump(hostDetails, databaseName, scriptsPath + "/" + scriptName);
  }
}
