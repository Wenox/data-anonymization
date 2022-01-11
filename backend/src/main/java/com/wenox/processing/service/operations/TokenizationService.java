package com.wenox.processing.service.operations;


import com.wenox.anonymisation.domain.Tokenization;
import com.wenox.processing.domain.Pair;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TokenizationService implements AnonymisationService<Tokenization> {

  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, Tokenization tokenization) {
    List<String> rawValues = rows.stream().map(Pair::getSecond).toList();

    final int step = tokenization.getStep();

    int count = tokenization.getStartingValue();
    Map<String, Integer> valuesMap = new TreeMap<>();
    for (var value : rawValues) {
      if (value == null) {
        continue;
      }
      var token = valuesMap.get(value);
      if (token == null) {
        valuesMap.put(value, count);
        count += step;
      }
    }

    return rows.stream().map(row -> {
      if (row.getSecond() == null) {
        return Pair.of(row.getFirst(), String.valueOf(0));
      }
      var token = valuesMap.get(row.getSecond());
      return Pair.of(row.getFirst(), String.valueOf(token));
    }).toList();
  }
}
