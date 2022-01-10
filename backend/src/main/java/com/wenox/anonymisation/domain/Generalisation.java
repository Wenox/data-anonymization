package com.wenox.anonymisation.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "generalisations")
public class Generalisation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "rowShuffle")
  ColumnOperations columnOperations;

  @Enumerated(EnumType.STRING)
  private GeneralisationMode generalisationMode;

  private Integer minValue;

  private Integer maxValue;

  private Integer intervalSize;

  private Integer numberOfDistributions;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public GeneralisationMode getGeneralisationMode() {
    return generalisationMode;
  }

  public void setGeneralisationMode(GeneralisationMode generalisationMode) {
    this.generalisationMode = generalisationMode;
  }

  public ColumnOperations getColumnOperations() {
    return columnOperations;
  }

  public void setColumnOperations(ColumnOperations columnOperations) {
    this.columnOperations = columnOperations;
  }

  public Integer getMinValue() {
    return minValue;
  }

  public void setMinValue(Integer minValue) {
    this.minValue = minValue;
  }

  public Integer getMaxValue() {
    return maxValue;
  }

  public void setMaxValue(Integer maxValue) {
    this.maxValue = maxValue;
  }

  public Integer getIntervalSize() {
    return intervalSize;
  }

  public void setIntervalSize(Integer interval) {
    this.intervalSize = interval;
  }

  public Integer getNumberOfDistributions() {
    return numberOfDistributions;
  }

  public void setNumberOfDistributions(Integer numberOfDistributions) {
    this.numberOfDistributions = numberOfDistributions;
  }
}