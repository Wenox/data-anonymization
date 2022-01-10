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
@Table(name = "hashings")
public class Hashing {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "patternMasking")
  ColumnOperations columnOperations;

  @Enumerated(EnumType.STRING)
  private HashingMode hashingMode;

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

  public HashingMode getHashingMode() {
    return hashingMode;
  }

  public void setHashingMode(HashingMode hashingMode) {
    this.hashingMode = hashingMode;
  }
}
