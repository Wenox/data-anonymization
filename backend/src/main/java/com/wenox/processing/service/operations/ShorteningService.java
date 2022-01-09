package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.anonymisation.domain.Shortening;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ShorteningService {

  public List<Pair<String, String>> shorten(List<Pair<String, String>> rows, Shortening shortening) {
    List<String> fullValues = rows.stream().map(Pair::getSecond).toList();

    List<String> shortenedValues = new ArrayList<>(fullValues.size());
    final int length = shortening.getLength();
    final boolean endsWithPeriod = shortening.getEndsWithPeriod();
    for (var value : fullValues) {
      if (value == null) {
        shortenedValues.add(null);
        continue;
      }

      if (value.length() <= length) {
        shortenedValues.add(value);
      } else {
        if (endsWithPeriod) {
          shortenedValues.add(value.substring(0, length) + ".");
        } else {
          shortenedValues.add(value.substring(0, length));
        }
      }
    }

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), shortenedValues.get(i)))
        .toList();
  }

}