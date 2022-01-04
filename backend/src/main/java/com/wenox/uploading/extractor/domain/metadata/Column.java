package com.wenox.uploading.extractor.domain.metadata;

public class Column {

  private final String columnName;
  private final String type; // todo: enum
  private final boolean nullable;

  public Column(String columnName, String type, String nullable) {
    this.columnName = columnName;
    this.type = type;
    this.nullable = "YES".equals(nullable);
    System.out.println("Created" + this);
  }

  public String getColumnName() {
    return columnName;
  }

  public String getType() {
    return type;
  }

  public boolean isNullable() {
    return nullable;
  }

  @Override
  public String toString() {
    return "Column{" + "columnName='" + columnName + '\'' +
        ", type='" + type + '\'' +
        ", isNullable=" + nullable +
        '}';
  }
}
