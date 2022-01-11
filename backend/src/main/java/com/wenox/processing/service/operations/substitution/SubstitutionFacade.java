package com.wenox.processing.service.operations.substitution;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.substitution.Substitution;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class SubstitutionFacade extends AnonymisationFacade {

  private final AnonymisationService<Substitution> anonymisationService = new SubstitutionService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Substitution substitution = operations.getSubstitution();
    if (substitution == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, substitution);
  }

  @Override
  protected AnonymisationService<Substitution> getAnonymisationService() {
    return anonymisationService;
  }
}
