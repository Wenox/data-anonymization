package com.wenox.processing.service;

import com.wenox.processing.domain.Pair;
import java.util.List;

public class SuppressionService {

  public List<Pair<String, String>> suppress(List<Pair<String, String>> rows, String value) {
    return rows.stream().map(pair -> Pair.of(pair.getFirst(), value)).toList();
  }
}
