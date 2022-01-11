package com.wenox.processing.service.operations.hashing;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Hashing;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class HashingFacade extends AnonymisationFacade {

  private final AnonymisationService<Hashing> anonymisationService = new HashingService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    var hashing = operations.getHashing();
    if (hashing == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, hashing);
  }

  @Override
  protected AnonymisationService<Hashing> getAnonymisationService() {
    return anonymisationService;
  }
}
