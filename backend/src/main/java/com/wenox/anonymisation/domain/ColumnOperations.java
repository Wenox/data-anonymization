package com.wenox.anonymisation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "column_operations")
public class ColumnOperations {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Worksheet worksheet;

  @OneToOne
  private Suppression suppression;

  @OneToOne
  private Shuffle shuffle;

  private String tableName;

  private String columnName;

  // todo: column type

  private String primaryKeyColumnName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Worksheet getWorksheet() {
    return worksheet;
  }

  public Suppression getSuppression() {
    return suppression;
  }

  public void setSuppression(Suppression suppression) {
    this.suppression = suppression;
  }

  public void setWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public Shuffle getShuffle() {
    return shuffle;
  }

  public void setShuffle(Shuffle shuffle) {
    this.shuffle = shuffle;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }

  public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
    this.primaryKeyColumnName = primaryKeyColumnName;
  }
}
