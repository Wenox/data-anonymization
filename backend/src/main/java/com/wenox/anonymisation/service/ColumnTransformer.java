package com.wenox.anonymisation.service;

import java.util.List;

public interface ColumnTransformer<T, U> {

  List<U> transform(List<T> rows);
}
