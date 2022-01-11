package com.wenox.anonymisation.dto.columnoperations;

import com.wenox.anonymisation.domain.rowshuffle.RowShuffle;
import javax.validation.constraints.NotNull;

public class AddRowShuffleRequest extends AddColumnShuffleRequest {

  @NotNull
  private RowShuffle.LetterMode letterMode;

  public RowShuffle.LetterMode getLetterMode() {
    return letterMode;
  }

  public void setLetterMode(RowShuffle.LetterMode letterMode) {
    this.letterMode = letterMode;
  }
}
