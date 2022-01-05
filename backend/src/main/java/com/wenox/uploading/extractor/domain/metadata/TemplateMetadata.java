package com.wenox.uploading.extractor.domain.metadata;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TemplateMetadata {

  private int numberOfTables = 0;
  private final Map<String, Table> tables = new HashMap<>();

  public void insertTable(Table table) {
    tables.put(table.getTableName(), table);
    numberOfTables++;
  }

  public Table getTable(String table) {
    return tables.get(table);
  }

  public int getNumberOfTables() {
    return numberOfTables;
  }

  public void setNumberOfTables(int numberOfTables) {
    this.numberOfTables = numberOfTables;
  }

  public Map<String, Table> getTables() {
    return tables;
  }
}
