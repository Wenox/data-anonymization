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
@Table(name = "perturbations")
public class Perturbation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "perturbation")
  ColumnOperations columnOperations;

  @Enumerated(EnumType.STRING)
  PerturbationMode perturbationMode;

  private Integer minValue;

  private Integer maxValue;

  private Integer value;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ColumnOperations getColumnOperations() {
    return columnOperations;
  }

  public void setColumnOperations(ColumnOperations columnOperations) {
    this.columnOperations = columnOperations;
  }

  public PerturbationMode getPerturbationMode() {
    return perturbationMode;
  }

  public void setPerturbationMode(PerturbationMode perturbationMode) {
    this.perturbationMode = perturbationMode;
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

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }
}