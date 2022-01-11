package com.wenox.processing.service.operations.suppression;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.suppression.Suppression;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class SuppressionFacade extends AnonymisationFacade {

  private final AnonymisationService<Suppression> anonymisationService = new SuppressionService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Suppression suppression = operations.getSuppression();
    if (suppression == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, suppression);
  }

  @Override
  protected AnonymisationService<Suppression> getAnonymisationService() {
    return anonymisationService;
  }
}
