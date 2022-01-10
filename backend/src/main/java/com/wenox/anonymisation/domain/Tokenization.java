package com.wenox.anonymisation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tokenizations")
public class Tokenization {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "generalisation")
  ColumnOperations columnOperations;

  private Integer startingValue;

  private Integer step;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ColumnOperations getColumnOperations() {
    return columnOperations;
  }

  public Integer getStartingValue() {
    return startingValue;
  }

  public void setStartingValue(Integer startingValue) {
    this.startingValue = startingValue;
  }

  public Integer getStep() {
    return step;
  }

  public void setStep(Integer step) {
    this.step = step;
  }

  public void setColumnOperations(ColumnOperations columnOperations) {
    this.columnOperations = columnOperations;
  }
}