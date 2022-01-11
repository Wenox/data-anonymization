package com.wenox.processing.service.operations.suppression;

import com.wenox.anonymisation.domain.suppression.Suppression;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.operations.AnonymisationService;
import java.sql.Types;
import java.util.List;

public class SuppressionService implements AnonymisationService<Suppression> {

  @Override
  public boolean altersTypeToText(String type) {
    return !String.valueOf(Types.VARCHAR).equals(type);
  }

  @Override
  public List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, Suppression suppression) {
    return rows.stream().map(pair -> Pair.of(pair.getFirst(), suppression.getSuppressionToken())).toList();
  }
}
