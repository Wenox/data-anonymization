package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.RowShuffle;
import com.wenox.processing.domain.Pair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RowShuffler {

  private final Random rng = new Random(System.currentTimeMillis());

  public List<Pair<String, String>> shuffle(List<Pair<String, String>> rows, RowShuffle.LetterMode letterMode) {
    List<String> shuffled = rows.stream()
        .map(Pair::getSecond)
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

    return IntStream.range(0, rows.size())
        .mapToObj(i -> Pair.of(rows.get(i).getFirst(), shuffled.get(i)))
        .toList();
  }

  public List<Pair<String, String>> shuffleWithRepetitions(List<Pair<String, String>> rows, RowShuffle.LetterMode letterMode) {
    List<String> shuffled = rows
        .stream()
        .map(Pair::getSecond)
        .map(row -> {
          if (row == null) {
            return null;
          }

          List<Character> shuffledWord = new ArrayList<>(row.length());
          for (int i = 0; i < row.length(); i++) {
            shuffledWord.add(row.charAt(rng.nextInt(row.length())));
          }

          return shuffledWord.stream()
              .map(String::valueOf)
              .map(v -> switch (letterMode) {
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
