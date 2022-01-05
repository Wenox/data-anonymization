package com.wenox.anonymisation.dto;

import com.wenox.anonymisation.domain.Operation;

public class OperationResponse {

  private String id;
  private final String operationName = "Generalisation";
  private String tableName;
  private String columnName;

  public static OperationResponse from(Operation operation) {
    var dto = new OperationResponse();
    dto.setId(operation.getId());
    dto.setTableName(operation.getTableName());
    dto.setColumnName(operation.getColumnName());
    return dto;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOperationName() {
    return operationName;
  }

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
}
