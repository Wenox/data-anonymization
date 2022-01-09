package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.processing.domain.Pair;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RowShuffler {

  public List<Pair<String, String>> shuffle(List<Pair<String, String>> rows, RowShuffle.LetterMode letterMode) {
    System.out.println("Before shuffle:");
    List<String> beforeShuffle = rows.stream().map(Pair::getSecond).collect(Collectors.toList());
    beforeShuffle.forEach(System.out::println);

    List<String> afterShuffle = beforeShuffle.stream()
        .map(row -> {
          if (row == null) {
            return null;
          }
          List<Character> word = row.chars().mapToObj(v -> (char) v).collect(Collectors.toList());
          Collections.shuffle(word);
          return word.stream()
              .map(String::valueOf)
              .map(v -> switch (letterMode) {
                case RETAIN_CASE -> v;
                case TO_LOWERCASE -> v.toLowerCase();
                case TO_UPPERCASE -> v.toUpperCase();
              })
              .collect(Collectors.joining());
        }).toList();

    System.out.println("After shuffle:");
    afterShuffle.forEach(System.out::println);

    return IntStream.range(0, rows.size()).mapToObj(i -> Pair.of(rows.get(i).getFirst(), afterShuffle.get(i))).toList();
  }
}
