package com.wenox.anonymisation.domain.shortening;

import com.wenox.anonymisation.domain.ColumnOperations;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "shortenings")
public class Shortening {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "shortening")
  ColumnOperations columnOperations;

  private Integer length;

  private Boolean endsWithPeriod;

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

  public Boolean getEndsWithPeriod() {
    return endsWithPeriod;
  }

  public void setEndsWithPeriod(Boolean endsWithPeriod) {
    this.endsWithPeriod = endsWithPeriod;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }
}
