package com.wenox.processing.service.operations.rowshuffle;

import com.wenox.anonymisation.domain.rowshuffle.RowShuffle;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RowShuffleService implements AnonymisationService<RowShuffle> {

  private final Random rng = new Random(System.currentTimeMillis());

  @Override
  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, RowShuffle rowShuffle) {
    List<String> shuffled = rows.stream()
        .map(Pair::getSecond)
        .map(row -> {
          if (row == null) {
            return null;
          }

          final List<Character> word;
          if (!rowShuffle.isWithRepetitions()) {
            word = row.chars().mapToObj(v -> (char) v).collect(Collectors.toList());
            Collections.shuffle(word);
          } else {
            word = new ArrayList<>(row.length());
            for (int i = 0; i < row.length(); i++) {
              word
                  .add(row.charAt(rng.nextInt(row.length())));
            }
          }
          return word.stream()
              .map(String::valueOf)
              .map(v -> switch (rowShuffle.getLetterMode()) {
                case RETAIN_CASE -> v;
                case TO_LOWERCASE -> v.toLowerCase();
                case TO_UPPERCASE -> v.toUpperCase();
              })
              .collect(Collectors.joining());
        }).toList();

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), shuffled.get(i)))
        .toList();
  }
}
