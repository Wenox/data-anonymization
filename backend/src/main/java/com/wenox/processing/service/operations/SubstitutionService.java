package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.Substitution;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class SubstitutionService {

  public List<Pair<String, String>> substitute(List<Pair<String, String>> rows, Substitution substitute) {
    List<String> originalValues = rows.stream().map(Pair::getSecond).toList();

    List<String> substitutions = Arrays.asList(substitute.getValues().split("\\s*,\\s*"));

    List<String> newValues = new ArrayList<>(originalValues.size());
    int currentIndex = 0;
    final int substitutionsSize = substitutions.size();
    if (!substitute.getRememberMappings()) {
      for (var originalValue : originalValues) {
        if (originalValue == null) {
          newValues.add(null);
          continue;
        }
        newValues.add(substitutions.get(currentIndex % substitutionsSize));
        ++currentIndex;
      }

      return IntStream.range(0, rows.size())
          .mapToObj(i -> Pair.of(rows.get(i).getFirst(), newValues.get(i)))
          .toList();

    } else {
      Map<String, String> mappings = new HashMap<>();
      for (var originalValue : originalValues) {
        if (originalValue != null && mappings.get(originalValue) == null) {
          mappings.put(originalValue, substitutions.get(currentIndex % substitutionsSize));
          ++currentIndex;
        }
      }

      return IntStream.range(0, rows.size())
          .mapToObj(i -> {
            var originalValue = originalValues.get(i);
            if (originalValue == null) {
              return Pair.of(rows.get(i).getFirst(), "");
            } else {
              return Pair.of(rows.get(i).getFirst(), mappings.get(originalValue));
            }
          })
          .toList();
    }
  }
}
