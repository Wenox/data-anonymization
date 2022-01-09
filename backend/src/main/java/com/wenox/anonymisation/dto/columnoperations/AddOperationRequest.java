package com.wenox.anonymisation.dto.columnoperations;

public class AddOperationRequest {

  private String tableName;
  private String columnName;
  private String columnType;
  private String primaryKeyColumnName;
  private String primaryKeyColumnType;

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }

  public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
    this.primaryKeyColumnName = primaryKeyColumnName;
  }

  public String getColumnType() {
    return columnType;
  }

  public String getPrimaryKeyColumnType() {
    return primaryKeyColumnType;
  }

  public void setPrimaryKeyColumnType(String primaryKeyColumnType) {
    this.primaryKeyColumnType = primaryKeyColumnType;
  }

  public void setColumnType(String columnType) {
    this.columnType = columnType;
  }
}
