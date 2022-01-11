package com.wenox.processing.service.operations.tokenization;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Tokenization;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class TokenizationFacade extends AnonymisationFacade {

  private final AnonymisationService<Tokenization> anonymisationService = new TokenizationService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    Tokenization tokenization = operations.getTokenization();
    if (tokenization == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, tokenization);
  }

  @Override
  protected AnonymisationService<Tokenization> getAnonymisationService() {
    return anonymisationService;
  }
}
