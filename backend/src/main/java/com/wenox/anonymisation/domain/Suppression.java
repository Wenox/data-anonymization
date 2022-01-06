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
  Operation operation;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  private String suppressionToken;

  public String getSuppressionToken() {
    return suppressionToken;
  }

  public void setSuppressionToken(String suppressionToken) {
    this.suppressionToken = suppressionToken;
  }
}
