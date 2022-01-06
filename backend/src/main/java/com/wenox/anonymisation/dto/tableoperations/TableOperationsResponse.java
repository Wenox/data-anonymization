package com.wenox.anonymisation.dto.tableoperations;

import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import java.util.List;

public class TableOperationsResponse {

  private final String tableName;
  private final String primaryKeyColumnName;
  private final Integer numberOfRows;
  private final List<ColumnOperationsResponse> columnOperationResponses;

  public TableOperationsResponse(String tableName,
                                 String primaryKeyColumnName,
                                 Integer numberOfRows,
                                 List<ColumnOperationsResponse> columnOperationResponses) {
    this.tableName = tableName;
    this.primaryKeyColumnName = primaryKeyColumnName;
    this.numberOfRows = numberOfRows;
    this.columnOperationResponses = columnOperationResponses;
  }

  public String getTableName() {
    return tableName;
  }

  public Integer getNumberOfRows() {
    return numberOfRows;
  }

  public List<ColumnOperationsResponse> getColumnOperations() {
    return columnOperationResponses;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }
}
