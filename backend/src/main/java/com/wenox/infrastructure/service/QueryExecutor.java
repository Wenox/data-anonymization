package com.wenox.infrastructure.service;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.data.util.Pair;
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
            (rs, n) -> Pair.of(rs.getString(1), rs.getString(2))
        );
    System.out.println("pairs size: " + pairs.size());
    pairs.forEach(System.out::println);
    return pairs;
  }
}
