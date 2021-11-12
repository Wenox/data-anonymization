package com.wenox.anonymization.uploader.extractor.metadata;

import java.util.Map;

public class Table {
  private String name;
  private String schema;
  private PK primaryKey;
  private Map<String, FK> foreignKeys;
  private Map<String, Column> columns;
  private Integer numberOfRows;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }

  public PK getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(PK primaryKey) {
    this.primaryKey = primaryKey;
  }

  public Map<String, FK> getForeignKeys() {
    return foreignKeys;
  }

  public void setForeignKeys(Map<String, FK> foreignKeys) {
    this.foreignKeys = foreignKeys;
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
