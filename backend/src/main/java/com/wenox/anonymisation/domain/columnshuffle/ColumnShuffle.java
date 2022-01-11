package com.wenox.anonymisation.domain.columnshuffle;

import com.wenox.anonymisation.domain.ColumnOperations;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "column_shuffles")
public class ColumnShuffle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "columnShuffle")
  ColumnOperations columnOperations;

  private boolean withRepetitions;

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

  public boolean isWithRepetitions() {
    return withRepetitions;
  }

  public void setWithRepetitions(boolean withRepetitions) {
    this.withRepetitions = withRepetitions;
  }
}
