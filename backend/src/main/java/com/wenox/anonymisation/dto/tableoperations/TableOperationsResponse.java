package com.wenox.anonymisation.dto.tableoperations;

import com.wenox.anonymisation.dto.columnoperations.ColumnOperationsResponse;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import java.util.List;

public class TableOperationsResponse {

  private final String tableName;
  private final String primaryKeyColumnName;
  private final String primaryKeyColumnType;
  private final Integer numberOfRows;
  private final List<ColumnOperationsResponse> listOfColumnOperations;
  private final TemplateMetadata metadata;

  public TableOperationsResponse(String tableName,
                                 String primaryKeyColumnName,
                                 String primaryKeyColumnType,
                                 Integer numberOfRows,
                                 List<ColumnOperationsResponse> listOfColumnOperations,
                                 TemplateMetadata metadata) {
    this.tableName = tableName;
    this.primaryKeyColumnName = primaryKeyColumnName;
    this.primaryKeyColumnType = primaryKeyColumnType;
    this.numberOfRows = numberOfRows;
    this.listOfColumnOperations = listOfColumnOperations;
    this.metadata = metadata;
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

  public TemplateMetadata getMetadata() {
    return metadata;
  }
}
