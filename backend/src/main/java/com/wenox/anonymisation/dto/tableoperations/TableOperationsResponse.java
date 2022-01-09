package com.wenox.anonymisation.dto.tableoperations;

import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import java.util.List;

public class TableOperationsResponse {

  private final String tableName;
  private final String primaryKeyColumnName;
  private final String primaryKeyColumnType;
  private final Integer numberOfRows;
  private final List<ColumnOperationsResponse> listOfColumnOperations;

  public TableOperationsResponse(String tableName,
                                 String primaryKeyColumnName,
                                 String primaryKeyColumnType,
                                 Integer numberOfRows,
                                 List<ColumnOperationsResponse> listOfColumnOperations) {
    this.tableName = tableName;
    this.primaryKeyColumnName = primaryKeyColumnName;
    this.primaryKeyColumnType = primaryKeyColumnType;
    this.numberOfRows = numberOfRows;
    this.listOfColumnOperations = listOfColumnOperations;
  }

  public String getTableName() {
    return tableName;
  }

  public Integer getNumberOfRows() {
    return numberOfRows;
  }

  public String getPrimaryKeyColumnType() {
    return primaryKeyColumnType;
  }

  public List<ColumnOperationsResponse> getListOfColumnOperations() {
    return listOfColumnOperations;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }
}
