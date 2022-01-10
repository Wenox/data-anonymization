package com.wenox.uploading.extractor.domain.metadata;

public class ForeignKey {
  private String referencingTableName;
  private String referencingColumnName;
  private String referencedTableName;
  private String referencedColumnName;
  private String foreignKeyName;
  private String primaryKeyName;
  private String type;

  public String getReferencingTableName() {
    return referencingTableName;
  }

  public void setReferencingTableName(String referencingTableName) {
    this.referencingTableName = referencingTableName;
  }

  public String getReferencingColumnName() {
    return referencingColumnName;
  }

  public void setReferencingColumnName(String referencingColumnName) {
    this.referencingColumnName = referencingColumnName;
  }

  public String getReferencedTableName() {
    return referencedTableName;
  }

  public void setReferencedTableName(String referencedTableName) {
    this.referencedTableName = referencedTableName;
  }

  public String getReferencedColumnName() {
    return referencedColumnName;
  }

  public void setReferencedColumnName(String referencedColumnName) {
    this.referencedColumnName = referencedColumnName;
  }

  public String getForeignKeyName() {
    return foreignKeyName;
  }

  public void setForeignKeyName(String foreignKeyName) {
    this.foreignKeyName = foreignKeyName;
  }

  public String getPrimaryKeyName() {
    return primaryKeyName;
  }

  public void setPrimaryKeyName(String primaryKeyName) {
    this.primaryKeyName = primaryKeyName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
