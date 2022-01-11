package com.wenox.processing.service.query;

import com.wenox.processing.domain.Pair;
import java.util.List;

public interface QuerySelector {

  List<Pair<String, String>> select(String tableName, String primaryKeyColumnName, String columnName);
}
