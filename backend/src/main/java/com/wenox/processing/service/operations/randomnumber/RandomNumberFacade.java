package com.wenox.processing.service.operations.randomnumber;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.randomnumber.RandomNumber;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationFacade;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.List;

public class RandomNumberFacade extends AnonymisationFacade {

  private final AnonymisationService<RandomNumber> anonymisationService = new RandomNumberService();

  @Override
  protected List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations, List<Pair<String, String>> rows) {
    RandomNumber randomNumber = operations.getRandomNumber();
    if (randomNumber == null) {
      return null;
    }
    return anonymisationService.anonymise(rows, randomNumber);
  }

  @Override
  protected AnonymisationService<RandomNumber> getAnonymisationService() {
    return anonymisationService;
  }
}
