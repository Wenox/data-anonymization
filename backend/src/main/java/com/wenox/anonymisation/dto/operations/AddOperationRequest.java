package com.wenox.anonymisation.dto.operations;

public class AddOperationRequest {

  private String tableName;
  private String columnName;
  private String primaryKeyColumnName;
  private String operationName;

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

  public String getOperationName() {
    return operationName;
  }

  public void setOperationName(String operationName) {
    this.operationName = operationName;
  }
}
