package com.wenox.processing.service.operations.perturbation;

import com.wenox.anonymisation.domain.perturbation.Perturbation;
import com.wenox.anonymisation.domain.perturbation.PerturbationMode;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class PerturbationService implements AnonymisationService<Perturbation> {

  Random rng = new Random(System.currentTimeMillis());

  @Override
  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, Perturbation perturbation) {
    List<String> rawValues = rows.stream().map(Pair::getSecond).toList();

    Integer userMin = perturbation.getMinValue();
    Integer userMax = perturbation.getMaxValue();
    Integer perturbationValue = perturbation.getValue();

    List<String> newValues = new ArrayList<>(rawValues.size());
    if (perturbation.getPerturbationMode() == PerturbationMode.FIXED) {
      for (var value : rawValues) {
        if (value == null) {
          newValues.add(null);
          continue;
        }
        int intValue = Integer.parseInt(value);
        int newValue = rng.nextInt(intValue - perturbationValue, intValue + perturbationValue + 1);
        if (userMin != null && newValue < userMin) {
          newValue = userMin;
        } else if (userMax != null && newValue > userMax) {
          newValue = userMax;
        }
        newValues.add(String.valueOf(newValue));
      }
    } else {
      for (var value : rawValues) {
        if (value == null) {
          newValues.add(null);
          continue;
        }
        int intValue = Integer.parseInt(value);
        double coefficient = rng.nextDouble(1 - (perturbationValue / 100.0), 1 + (perturbationValue / 100.0));
        int newValue = (int)(intValue * coefficient);
        if (userMin != null && newValue < userMin) {
          newValue = userMin;
        } else if (userMax != null && newValue > userMax) {
          newValue = userMax;
        }
        newValues.add(String.valueOf(newValue));
      }
    }
    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), newValues.get(i)))
        .toList();
  }
}
