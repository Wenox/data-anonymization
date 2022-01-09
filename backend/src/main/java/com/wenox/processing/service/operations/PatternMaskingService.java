package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.PatternMasking;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.apache.commons.lang3.RandomStringUtils;

public class PatternMaskingService {

  private final Random rng = new Random(System.currentTimeMillis());

  public List<Pair<String, String>> mask(List<Pair<String, String>> rows, PatternMasking patternMasking) {
    List<String> unmaskedValues = rows.stream().map(Pair::getSecond).toList();

    String pattern = patternMasking.getPattern();
    Boolean discardExcessiveCharacters = patternMasking.getDiscardExcessiveCharacters();

    List<String> maskedValues = new ArrayList<>(unmaskedValues.size());

    for (String unmaskedValue : unmaskedValues) {

      if (unmaskedValue == null) {
        maskedValues.add(null);
        continue;
      }

      final char[] valueChars = unmaskedValue.toCharArray();
      final int valueSize = valueChars.length;

      int i = 0;
      var masked = new StringBuilder();

      for (Character c : pattern.toCharArray()) {
        if (i >= valueSize) {
          break;
        }
        switch (c) {
          case 'O' -> masked.append(valueChars[i]);
          case 'X' -> masked.append('#');
          case 'N' -> masked.append(rng.nextInt(10));
          case 'L' -> masked.append((char) (rng.nextInt(26) + 'a'));
          case 'U' -> masked.append((char) (rng.nextInt(26) + 'A'));
          case 'A' -> masked.append(RandomStringUtils.randomAlphabetic(1));
          case 'C' -> masked.append(RandomStringUtils.randomAlphanumeric(1));
        }
        i++;
      }

      if (!discardExcessiveCharacters && i < valueSize) {
        for (int k = i; k < valueSize; k++) {
          masked.append(valueChars[k]);
        }
      }
      maskedValues.add(masked.toString());
    }

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), maskedValues.get(i)))
        .toList();
  }
}
