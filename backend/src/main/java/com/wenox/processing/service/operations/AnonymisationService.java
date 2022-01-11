package com.wenox.processing.service.operations;

import com.wenox.processing.domain.Pair;
import java.util.List;

public interface AnonymisationService<T> {

  List<Pair<String, String>> anonymise(List<Pair<String, String>> rows, T t);

  default boolean altersTypeToText(String type) {
    return false;
  }

  default boolean altersTypeToInteger(String type) {
    return false;
  }
}
