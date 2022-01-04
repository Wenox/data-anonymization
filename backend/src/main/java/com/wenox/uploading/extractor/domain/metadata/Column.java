package com.wenox.uploading.extractor.domain.metadata;

public class Column {

  private final String columnName;
  private final String type; // todo: enum
  private final boolean isNullable;

  public Column(String columnName, String type, String isNullable) {
    this.columnName = columnName;
    this.type = type;
    this.isNullable = "YES".equals(isNullable);
    System.out.println("Created" + this);
  }

  public String getColumnName() {
    return columnName;
  }

  public String getType() {
    return type;
  }

  public boolean getIsNullable() {
    return isNullable;
  }

  @Override
  public String toString() {
    return "Column{" + "columnName='" + columnName + '\'' +
        ", type='" + type + '\'' +
        ", isNullable=" + isNullable +
        '}';
  }
}
