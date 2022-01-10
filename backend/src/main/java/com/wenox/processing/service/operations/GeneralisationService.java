package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.Generalisation;
import com.wenox.anonymisation.domain.GeneralisationMode;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralisationService {

  public List<Pair<String, String>> generalise(List<Pair<String, String>> rows, Generalisation generalisation) {
    List<String> rawValues = rows.stream().map(Pair::getSecond).toList();

    int rawMin = rawValues.stream().mapToInt(Integer::valueOf).min().getAsInt();
    int rawMax = rawValues.stream().mapToInt(Integer::valueOf).max().getAsInt();

    Integer userMin = generalisation.getMinValue();
    Integer userMax = generalisation.getMaxValue();

    int computedMin = userMin == null ? rawMin : (userMin < rawMin ? userMin : rawMin);
    int computedMax = userMax == null ? rawMax : (userMax > rawMax ? userMax : rawMax);

    if (generalisation.getGeneralisationMode() == GeneralisationMode.DISTRIBUTION) {
      var numberOfDistributions = generalisation.getNumberOfDistributions();

      int distributionSize = (computedMax - computedMin) / numberOfDistributions;

      Map<GeneralisationInterval, List<Pair<String, String>>> generalisationMap = new HashMap<>();

      int currentStart = computedMin;
      for (int i = 0; i < numberOfDistributions; i++) {
        generalisationMap.put(new GeneralisationInterval(currentStart + 1, currentStart + distributionSize), new ArrayList<>());
        currentStart += distributionSize;
      }

      var intervals = generalisationMap.keySet();
      for (var value : rows) {
        for (var interval : intervals) {
          if (interval.isWithinInterval(Integer.valueOf(value.getSecond()))) {
            generalisationMap.get(interval).add(Pair.of(value.getFirst(), interval.toString()));
          }
        }
      }

      return generalisationMap.values().stream().flatMap(List::stream).toList();
    }

    else {



    }


    return null;
  }

  static class GeneralisationInterval {
    int startInclusive;
    int endInclusive;

    @Override
    public String toString() {
      return startInclusive + " - " + endInclusive;
    }

    public boolean isWithinInterval(Integer value) {
      return value >= startInclusive && value <= endInclusive;
    }

    public GeneralisationInterval(int startInclusive, int endInclusive) {
      this.startInclusive = startInclusive;
      this.endInclusive = endInclusive;
    }

    public int getStartInclusive() {
      return startInclusive;
    }

    public void setStartInclusive(int startInclusive) {
      this.startInclusive = startInclusive;
    }

    public int getEndInclusive() {
      return endInclusive;
    }

    public void setEndInclusive(int endInclusive) {
      this.endInclusive = endInclusive;
    }
  }
}
