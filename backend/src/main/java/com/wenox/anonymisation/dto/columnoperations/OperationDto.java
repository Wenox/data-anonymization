package com.wenox.anonymisation.dto.columnoperations;

import com.wenox.anonymisation.domain.ColumnOperations;

public class OperationDto {

  private String id;
  private final String operationName = "Generalisation";
  private String tableName;
  private String columnName;

  public static OperationDto from(ColumnOperations columnOperations) {
    var dto = new OperationDto();
    dto.setId(String.valueOf(columnOperations.getId()));
    dto.setTableName(columnOperations.getTableName());
    dto.setColumnName(columnOperations.getColumnName());
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
