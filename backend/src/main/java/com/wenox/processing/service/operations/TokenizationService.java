package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.Tokenization;
import com.wenox.processing.domain.Pair;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TokenizationService {

  public List<Pair<String, String>> tokenize(List<Pair<String, String>> rows, Tokenization tokenization) {
    List<String> rawValues = rows.stream().map(Pair::getSecond).toList();

    int count = 0;
    Map<String, Integer> valuesMap = new TreeMap<>();
    for (var value : rawValues) {
      var token = valuesMap.get(value);
      if (token == null) {
        valuesMap.put(value, ++count);
      }
    }

    return rows.stream().map(row -> Pair.of(row.getFirst(), String.valueOf(valuesMap.get(row.getSecond())))).toList();
  }

}
