package com.wenox.anonymisation.service;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ColumnShuffler implements ColumnTransformer<String, String> {

  private static final Logger log = LoggerFactory.getLogger(ColumnShuffler.class);

  public List<String> transform(List<String> rows) {
    log.info("Shuffling {} rows.", rows.size());
    Collections.shuffle(rows);
    return rows;
  }
}
