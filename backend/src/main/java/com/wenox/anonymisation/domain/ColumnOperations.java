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
  private ColumnShuffle columnShuffle;

  @OneToOne
  private RowShuffle rowShuffle;

  @OneToOne
  private PatternMasking patternMasking;

  @OneToOne
  private Shortening shortening;

  @OneToOne
  private Generalisation generalisation;

  @OneToOne
  private Perturbation perturbation;

  @OneToOne
  private RandomNumber randomNumber;

  @OneToOne
  private Tokenization tokenization;

  @OneToOne
  private Hashing hashing;

  private String tableName;

  private String columnName;

  private String columnType;

  private String primaryKeyColumnName;

  private String primaryKeyColumnType;

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

  public RowShuffle getRowShuffle() {
    return rowShuffle;
  }

  public void setRowShuffle(RowShuffle rowShuffle) {
    this.rowShuffle = rowShuffle;
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

  public String getColumnType() {
    return columnType;
  }

  public void setColumnType(String columnType) {
    this.columnType = columnType;
  }

  public String getPrimaryKeyColumnType() {
    return primaryKeyColumnType;
  }

  public PatternMasking getPatternMasking() {
    return patternMasking;
  }

  public void setPatternMasking(PatternMasking patternMasking) {
    this.patternMasking = patternMasking;
  }

  public Shortening getShortening() {
    return shortening;
  }

  public void setShortening(Shortening shortening) {
    this.shortening = shortening;
  }

  public Generalisation getGeneralisation() {
    return generalisation;
  }

  public void setGeneralisation(Generalisation generalisation) {
    this.generalisation = generalisation;
  }

  public void setPrimaryKeyColumnType(String primaryKeyColumnType) {
    this.primaryKeyColumnType = primaryKeyColumnType;
  }

  public ColumnShuffle getColumnShuffle() {
    return columnShuffle;
  }

  public void setColumnShuffle(ColumnShuffle columnShuffle) {
    this.columnShuffle = columnShuffle;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }

  public void setPrimaryKeyColumnName(String primaryKeyColumnName) {
    this.primaryKeyColumnName = primaryKeyColumnName;
  }

  public Perturbation getPerturbation() {
    return perturbation;
  }

  public void setPerturbation(Perturbation perturbation) {
    this.perturbation = perturbation;
  }

  public RandomNumber getRandomNumber() {
    return randomNumber;
  }

  public void setRandomNumber(RandomNumber randomNumber) {
    this.randomNumber = randomNumber;
  }

  public Hashing getHashing() {
    return hashing;
  }

  public void setHashing(Hashing hashing) {
    this.hashing = hashing;
  }

  public Tokenization getTokenization() {
    return tokenization;
  }

  public void setTokenization(Tokenization tokenization) {
    this.tokenization = tokenization;
  }
}
