package com.wenox.processing.service.operations.randomnumber;

import com.wenox.anonymisation.domain.randomnumber.RandomNumber;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationService;
import java.sql.Types;
import java.util.List;
import java.util.Random;

public class RandomNumberService implements AnonymisationService<RandomNumber> {

  Random rng = new Random(System.currentTimeMillis());

  @Override
  public boolean altersTypeToInteger(String type) {
    return !String.valueOf(Types.INTEGER).equals(type);
  }

  @Override
  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, RandomNumber randomNumber) {
    Integer userMin = randomNumber.getMinValue();
    Integer userMax = randomNumber.getMaxValue();

    return rows
        .stream()
        .map(row -> Pair.of(row.getFirst(), String.valueOf(rng.nextInt(userMin, userMax + 1))))
        .toList();
  }
}
