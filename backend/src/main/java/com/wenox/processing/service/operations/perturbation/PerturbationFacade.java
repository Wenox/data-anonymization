package com.wenox.processing.service.operations.perturbation;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Perturbation;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class PerturbationFacade extends AnonymisationFacade {

  private final AnonymisationService<Perturbation> anonymisationService = new PerturbationService();

  @Override
  List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Perturbation perturbation = operations.getPerturbation();
    if (perturbation == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, perturbation);
  }

  @Override
  AnonymisationService<Perturbation> getAnonymisationService() {
    return anonymisationService;
  }
}
