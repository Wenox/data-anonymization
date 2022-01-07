package com.wenox.processing.service.operations;

import java.util.List;

public interface ColumnTransformer<T, U> {

  List<U> transform(List<T> rows);
}
