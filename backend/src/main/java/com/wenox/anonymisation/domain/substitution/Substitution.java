package com.wenox.anonymisation.domain.substitution;

import com.wenox.anonymisation.domain.ColumnOperations;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "substitutions")
public class Substitution {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "generalisation")
  ColumnOperations columnOperations;

  private String values;

  private Boolean rememberMappings;

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

  public String getValues() {
    return values;
  }

  public void setValues(String values) {
    this.values = values;
  }

  public Boolean getRememberMappings() {
    return rememberMappings;
  }

  public void setRememberMappings(Boolean rememberMappings) {
    this.rememberMappings = rememberMappings;
  }
}
