package com.wenox.anonymization.uploader.extractor.metadata;

import java.util.Map;

public class Schema {
  private String name;
  private Map<String, Table> tables;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Table> getTables() {
    return tables;
  }

  public void setTables(Map<String, Table> tables) {
    this.tables = tables;
  }
}
