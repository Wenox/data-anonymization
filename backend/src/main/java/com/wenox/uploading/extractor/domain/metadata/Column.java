package com.wenox.uploading.extractor.domain.metadata;

public class Column {

  private final String columnName;
  private final String type; // todo: enum
  private final boolean nullable;
  private final boolean primaryKey;
  private final boolean foreignKey;

  public Column(String columnName, String type, String nullable, boolean primaryKey, boolean foreignKey) {
    this.columnName = columnName;
    this.type = type;
    this.nullable = "YES".equals(nullable);
    this.primaryKey = primaryKey;
    this.foreignKey = foreignKey;
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

  public boolean isPrimaryKey() {
    return primaryKey;
  }

  public boolean isForeignKey() {
    return foreignKey;
  }
}
