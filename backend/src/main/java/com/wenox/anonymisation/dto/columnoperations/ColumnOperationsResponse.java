package com.wenox.anonymisation.dto.columnoperations;

import com.wenox.uploading.domain.metadata.Column;
import java.util.List;

public class ColumnOperationsResponse {

  private Column column;

  private List<ColumnOperationDto> listOfColumnOperation;

  public Column getColumn() {
    return column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public List<ColumnOperationDto> getListOfColumnOperation() {
    return listOfColumnOperation;
  }

  public void setListOfColumnOperation(List<ColumnOperationDto> listOfColumnOperation) {
    this.listOfColumnOperation = listOfColumnOperation;
  }
}
