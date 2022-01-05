package com.wenox.anonymisation.dto;

import com.wenox.uploading.extractor.domain.metadata.Column;
import java.util.List;

public class ColumnOperationsResponse {

  private Column column;
  private List<OperationResponse> operations;

  public Column getColumn() {
    return column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public List<OperationResponse> getOperations() {
    return operations;
  }

  public void setOperations(List<OperationResponse> operations) {
    this.operations = operations;
  }
}
