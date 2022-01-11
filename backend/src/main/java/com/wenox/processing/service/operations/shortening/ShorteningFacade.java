package com.wenox.processing.service.operations.shortening;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Shortening;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class ShorteningFacade extends AnonymisationFacade {

  private final AnonymisationService<Shortening> anonymisationService = new ShorteningService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Shortening shortening = operations.getShortening();
    if (shortening == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, shortening);
  }

  @Override
  protected AnonymisationService<Shortening> getAnonymisationService() {
    return anonymisationService;
  }
}
