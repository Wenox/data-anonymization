package com.wenox.processing.service.operations.columnshuffle;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.columnshuffle.ColumnShuffle;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class ColumnShuffleFacade extends AnonymisationFacade {

  private final AnonymisationService<ColumnShuffle> anonymisationService = new ColumnShuffleService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    ColumnShuffle columnShuffle = operations.getColumnShuffle();
    if (columnShuffle == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, columnShuffle);
  }

  @Override
  protected AnonymisationService<ColumnShuffle> getAnonymisationService() {
    return anonymisationService;
  }
}
