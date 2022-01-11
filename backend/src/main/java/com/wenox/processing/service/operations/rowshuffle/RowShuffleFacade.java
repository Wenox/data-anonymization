package com.wenox.processing.service.operations.rowshuffle;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.rowshuffle.RowShuffle;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class RowShuffleFacade extends AnonymisationFacade {

  private final AnonymisationService<RowShuffle> anonymisationService = new RowShuffleService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    RowShuffle rowShuffle = operations.getRowShuffle();
    if (rowShuffle == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, rowShuffle);
  }

  @Override
  protected AnonymisationService<RowShuffle> getAnonymisationService() {
    return anonymisationService;
  }
}
