package com.wenox.uploading.extractor.domain.metadata;

import java.util.Map;

public class Table {
  private String tableName;
  private String schemaName;
  private Map<String, PrimaryKey> primaryKeys;
  private Map<String, ForeignKey> importedKeys;
  private Map<String, ForeignKey> exportedKeys;
  private Map<String, Column> columns;
  private Integer numberOfRows;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getSchemaName() {
    return schemaName;
  }

  public void setSchemaName(String schemaName) {
    this.schemaName = schemaName;
  }

  public Map<String, PrimaryKey> getPrimaryKeys() {
    return primaryKeys;
  }

  public void setPrimaryKeys(Map<String, PrimaryKey> primaryKeys) {
    this.primaryKeys = primaryKeys;
  }

  public Map<String, ForeignKey> getImportedKeys() {
    return importedKeys;
  }

  public void setImportedKeys(Map<String, ForeignKey> importedKeys) {
    this.importedKeys = importedKeys;
  }

  public Map<String, ForeignKey> getExportedKeys() {
    return exportedKeys;
  }

  public void setExportedKeys(
      Map<String, ForeignKey> exportedKeys) {
    this.exportedKeys = exportedKeys;
  }

  public Map<String, Column> getColumns() {
    return columns;
  }

  public void setColumns(Map<String, Column> columns) {
    this.columns = columns;
  }

  public Integer getNumberOfRows() {
    return numberOfRows;
  }

  public void setNumberOfRows(Integer numberOfRows) {
    this.numberOfRows = numberOfRows;
  }
}
