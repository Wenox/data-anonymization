package com.wenox.processing.service.operations.rowshuffle;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class RowShuffleFacade extends AnonymisationFacade {

  private final AnonymisationService<RowShuffle> anonymisationService = new RowShuffleService();

  @Override
  List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    RowShuffle rowShuffle = operations.getRowShuffle();
    if (rowShuffle == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, rowShuffle);
  }

  @Override
  AnonymisationService<RowShuffle> getAnonymisationService() {
    return anonymisationService;
  }
}
