package com.wenox.anonymisation.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "operations")
public class Operation {

  @Id
  private final String id = UUID.randomUUID().toString();

  @ManyToOne(fetch = FetchType.LAZY)
  private Worksheet worksheet;

  private String name;

  public String getId() {
    return id;
  }

  public Worksheet getWorksheet() {
    return worksheet;
  }

  public void setWorksheet(Worksheet worksheet) {
    this.worksheet = worksheet;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
