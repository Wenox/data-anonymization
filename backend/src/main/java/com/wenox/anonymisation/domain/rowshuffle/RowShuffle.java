package com.wenox.anonymisation.domain.rowshuffle;

import com.wenox.anonymisation.domain.ColumnOperations;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "row_shuffles")
public class RowShuffle {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "rowShuffle")
  ColumnOperations columnOperations;

  @Enumerated(EnumType.STRING)
  private LetterMode letterMode;

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

  public LetterMode getLetterMode() {
    return letterMode;
  }

  public void setLetterMode(LetterMode letterMode) {
    this.letterMode = letterMode;
  }

  public boolean isWithRepetitions() {
    return withRepetitions;
  }

  public void setWithRepetitions(boolean withRepetitions) {
    this.withRepetitions = withRepetitions;
  }

  public enum LetterMode {
    RETAIN_CASE,
    TO_LOWERCASE,
    TO_UPPERCASE
  }
}