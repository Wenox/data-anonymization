package com.wenox.processing.service.operations;

import com.wenox.processing.domain.Pair;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

public class ColumnShuffler {

  private static final Logger log = LoggerFactory.getLogger(ColumnShuffler.class);

  public List<Pair<String, String>> shuffle(List<Pair<String, String>> rows) {
    log.info("Shuffling {} rows.", rows.size());

    System.out.println("Before shuffle: ");
    rows.forEach(System.out::println);

    var values = rows.stream().map(Pair::getSecond).collect(Collectors.toList());
    Collections.shuffle(values);

    var shuffledRows = IntStream.range(0, rows.size()).mapToObj(i -> Pair.of(rows.get(i).getFirst(), values.get(i))).toList();

    System.out.println("After shuffle:");
    shuffledRows.forEach(System.out::println);

    return shuffledRows;
  }

  public List<Pair<String, String>> shuffleWithRepetitions(List<Pair<String, String>> rows) {
    log.info("Shuffling {} rows.", rows.size());

    System.out.println("Before shuffle: ");
    rows.forEach(System.out::println);

    var values = rows.stream().map(Pair::getSecond).collect(Collectors.toList());
    Collections.shuffle(values);

    var shuffledRows = IntStream.range(0, rows.size()).mapToObj(i -> Pair.of(rows.get(i).getFirst(), values.get(i))).toList();

    System.out.println("After shuffle:");
    shuffledRows.forEach(System.out::println);

    return shuffledRows;
  }
}
