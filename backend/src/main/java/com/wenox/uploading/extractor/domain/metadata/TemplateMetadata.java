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

  public List<Column> getColumnsByTable(String table) {
    System.out.println("getting for table: " + table);
    return tables.get(table).getColumns().values().stream().toList();
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
