package com.wenox.anonymization.uploader.extractor.metadata;

import java.util.HashMap;
import java.util.Map;

public class WorksheetTemplateMetadata {
  private Map<String, Schema> schemas = new HashMap<>();
  private int numberOfSchemas;
  private int numberOfTables;

  public Map<String, Schema> getSchemas() {
    return schemas;
  }

  public void setSchemas(Map<String, Schema> schemas) {
    this.schemas = schemas;
  }

  public int getNumberOfSchemas() {
    return numberOfSchemas;
  }

  public void setNumberOfSchemas(int numberOfSchemas) {
    this.numberOfSchemas = numberOfSchemas;
  }

  public int getNumberOfTables() {
    return numberOfTables;
  }

  public void setNumberOfTables(int numberOfTables) {
    this.numberOfTables = numberOfTables;
  }
}