package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.Suppression;
import com.wenox.processing.domain.Pair;
import java.util.List;

public class SuppressionService implements AnonymisationService<Suppression>  {

  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, Suppression suppression) {
    return rows.stream().map(pair -> Pair.of(pair.getFirst(), suppression.getSuppressionToken())).toList();
  }
}
