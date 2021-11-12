package com.wenox.anonymization.uploader.extractor.metadata;

public class FK {
  private String name;
  private String type;
  private String referencedColumn;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getReferencedColumn() {
    return referencedColumn;
  }

  public void setReferencedColumn(String referencedColumn) {
    this.referencedColumn = referencedColumn;
  }
}
