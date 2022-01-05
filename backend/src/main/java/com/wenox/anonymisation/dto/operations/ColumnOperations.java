package com.wenox.anonymisation.dto.operations;

import com.wenox.uploading.extractor.domain.metadata.Column;
import java.util.List;

public class ColumnOperations {

  private Integer numberOfRows;
  private Column column;
  private List<OperationDto> operationDtos;

  public void setNumberOfRows(Integer numberOfRows) {
    this.numberOfRows = numberOfRows;
  }

  public Column getColumn() {
    return column;
  }

  public void setColumn(Column column) {
    this.column = column;
  }

  public List<OperationDto> getOperations() {
    return operationDtos;
  }

  public void setOperations(List<OperationDto> operationDtos) {
    this.operationDtos = operationDtos;
  }
}
