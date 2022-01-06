package com.wenox.processing.service;

import com.wenox.processing.domain.Pair;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class QueryExecutor {

  private final JdbcTemplate jdbcTemplate;

  public QueryExecutor(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  public List<Pair<String, String>> select(String tableName, String primaryKeyColumnName, String columnName) {
    System.out.println("Selecting " + tableName + ": " + primaryKeyColumnName + " and " + columnName);
    List<Pair<String, String>> pairs =
        jdbcTemplate.query(String.format("SELECT %s, %s FROM %s", primaryKeyColumnName, columnName, tableName),
            (rs, n) -> {
              var pk = rs.getString(primaryKeyColumnName);
              System.out.println("PK: " + pk);
              var column = rs.getString(columnName);
              System.out.println("column: " + column);
              return Pair.of(pk, column);
            }
        );
    System.out.println("pairs size: " + pairs.size());
    pairs.forEach(System.out::println);
    return pairs;
  }
}
