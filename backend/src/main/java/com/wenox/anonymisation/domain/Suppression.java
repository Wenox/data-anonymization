package com.wenox.anonymisation.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "suppressions")
public class Suppression {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "suppression")
  ColumnOperations columnOperations;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ColumnOperations getColumnOperations() {
    return columnOperations;
  }

  public void setColumnOperations(ColumnOperations operation) {
    this.columnOperations = operation;
  }

  private String suppressionToken;

  public String getSuppressionToken() {
    return suppressionToken;
  }

  public void setSuppressionToken(String suppressionToken) {
    this.suppressionToken = suppressionToken;
  }
}
