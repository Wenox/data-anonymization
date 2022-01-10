package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.RandomNumber;
import com.wenox.processing.domain.Pair;
import java.util.List;
import java.util.Random;

public class RandomNumberService {

  Random rng = new Random(System.currentTimeMillis());

  public List<Pair<String, String>> randomize(List<Pair<String, String>> rows, RandomNumber randomNumber) {
    Integer userMin = randomNumber.getMinValue();
    Integer userMax = randomNumber.getMaxValue();

    return rows
        .stream()
        .map(row -> Pair.of(row.getFirst(), String.valueOf(rng.nextInt(userMin, userMax + 1))))
        .toList();
  }
}
