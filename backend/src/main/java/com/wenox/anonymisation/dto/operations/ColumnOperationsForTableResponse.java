package com.wenox.anonymisation.dto.operations;

import java.util.List;

public class ColumnOperationsForTableResponse {

  private final String tableName;
  private final String primaryKeyColumnName;
  private final Integer numberOfRows;
  private final List<ColumnOperations> columnOperations;

  public ColumnOperationsForTableResponse(String tableName,
                                          String primaryKeyColumnName,
                                          Integer numberOfRows,
                                          List<ColumnOperations> columnOperations) {
    this.tableName = tableName;
    this.primaryKeyColumnName = primaryKeyColumnName;
    this.numberOfRows = numberOfRows;
    this.columnOperations = columnOperations;
  }

  public String getTableName() {
    return tableName;
  }

  public Integer getNumberOfRows() {
    return numberOfRows;
  }

  public List<ColumnOperations> getColumnOperations() {
    return columnOperations;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }
}
