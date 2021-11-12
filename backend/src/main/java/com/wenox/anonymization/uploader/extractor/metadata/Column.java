package com.wenox.anonymization.uploader.extractor.metadata;

public class Column {
  private String name;
  private String type; // todo: enum
  private String nullable;

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

  public String isNullable() {
    return nullable;
  }

  public void setNullable(String nullable) {
    this.nullable = nullable;
  }
}
