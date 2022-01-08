package com.wenox.uploading.extractor.domain.metadata;

import java.util.HashMap;
import java.util.Map;

public class Table {
  private final String tableName;
  private final Integer numberOfRows;
  private final Integer numberOfColumns;
  private final Map<String, Column> columns = new HashMap<>();
  private PrimaryKey primaryKey;
  private final Map<String, ForeignKey> importedKeys = new HashMap<>();
  private final Map<String, ForeignKey> exportedKeys = new HashMap<>();

  public Table(String tableName, Integer numberOfRows, Integer numberOfColumns) {
    this.tableName = tableName;
    this.numberOfRows = numberOfRows;
    this.numberOfColumns = numberOfColumns;
  }

  public void insertColumn(Column column) {
    columns.put(column.getColumnName(), column);
  }

  public String getTableName() {
    return tableName;
  }

  public Integer getNumberOfRows() {
    return numberOfRows;
  }

  public Integer getNumberOfColumns() {
    return numberOfColumns;
  }

  public Map<String, Column> getColumns() {
    return columns;
  }

  public PrimaryKey getPrimaryKey() {
    return primaryKey;
  }

  public Map<String, ForeignKey> getImportedKeys() {
    return importedKeys;
  }

  public Map<String, ForeignKey> getExportedKeys() {
    return exportedKeys;
  }

  public void setPrimaryKey(PrimaryKey primaryKey) {
    this.primaryKey = primaryKey;
  }

  @Override
  public String toString() {
    return "Table{" + "tableName='" + tableName + '\'' +
        ", numberOfRows=" + numberOfRows +
        ", numberOfColumns=" + numberOfColumns +
        ", columns=" + columns +
        ", primaryKeys=" + primaryKey +
        ", importedKeys=" + importedKeys +
        ", exportedKeys=" + exportedKeys +
        '}';
  }
}
