package com.wenox.anonymisation.domain.patternmasking;

import com.wenox.anonymisation.domain.ColumnOperations;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pattern_maskings")
public class PatternMasking {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id;

  @OneToOne(mappedBy = "patternMasking")
  ColumnOperations columnOperations;

  private String pattern;

  private Boolean discardExcessiveCharacters;

  private Character maskingCharacter;

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

  public Character getMaskingCharacter() {
    return maskingCharacter;
  }

  public void setMaskingCharacter(Character maskingCharacter) {
    this.maskingCharacter = maskingCharacter;
  }

  public String getPattern() {
    return pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public Boolean getDiscardExcessiveCharacters() {
    return discardExcessiveCharacters;
  }

  public void setDiscardExcessiveCharacters(Boolean discardExcessiveCharacters) {
    this.discardExcessiveCharacters = discardExcessiveCharacters;
  }
}