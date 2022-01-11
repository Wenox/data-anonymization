package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.ColumnShuffle;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ColumnShuffleService implements AnonymisationService<ColumnShuffle> {

  private final Random rng = new Random(System.currentTimeMillis());

  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, ColumnShuffle columnShuffle) {
    List<String> values = rows.stream().map(Pair::getSecond).collect(Collectors.toList());
    if (!columnShuffle.isWithRepetitions()) {
      Collections.shuffle(values);
      return IntStream.range(0, rows.size()).mapToObj(i -> Pair.of(rows.get(i).getFirst(), values.get(i))).toList();
    } else {
      var afterShuffle = shuffleWithRepetitions(values);
      return IntStream.range(0, rows.size()).mapToObj(i -> Pair.of(rows.get(i).getFirst(), afterShuffle.get(i))).toList();
    }
  }

  private List<String> shuffleWithRepetitions(List<String> beforeShuffle) {
    final int valuesSize = beforeShuffle.size();
    List<String> afterShuffle = new ArrayList<>(valuesSize);
    for (int i = 0; i < valuesSize; i++) {
      afterShuffle.add(beforeShuffle.get(rng.nextInt(valuesSize)));
    }
    return afterShuffle;
  }
}
