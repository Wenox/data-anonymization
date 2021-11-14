package com.wenox.anonymization.uploader.extractor.metadata;

import java.util.Map;

public class Schema {
  private String schemaName;
  private Map<String, Table> tables;

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public Map<String, Table> getTables() {
    return tables;
  }

  public void setTables(Map<String, Table> tables) {
    this.tables = tables;
  }
}
