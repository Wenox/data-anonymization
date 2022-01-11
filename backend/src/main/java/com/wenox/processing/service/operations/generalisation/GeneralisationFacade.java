package com.wenox.processing.service.operations.generalisation;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.generalisation.Generalisation;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class GeneralisationFacade extends AnonymisationFacade {

  private final AnonymisationService<Generalisation> anonymisationService = new GeneralisationService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Generalisation generalisation = operations.getGeneralisation();
    if (generalisation == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, generalisation);
  }

  @Override
  protected AnonymisationService<Generalisation> getAnonymisationService() {
    return anonymisationService;
  }
}