package com.wenox.processing.service.operations.patternmasking;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.PatternMasking;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class PatternMaskingFacade extends AnonymisationFacade {

  private final AnonymisationService<PatternMasking> anonymisationService = new PatternMaskingService();

  @Override
  List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    PatternMasking patternMasking = operations.getPatternMasking();
    if (patternMasking == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, patternMasking);
  }

  @Override
  AnonymisationService<PatternMasking> getAnonymisationService() {
    return anonymisationService;
  }
}
