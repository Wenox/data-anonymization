package com.wenox.processing.service.query;

import com.wenox.processing.domain.Pair;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateQuerySelector implements QuerySelector {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateQuerySelector(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public List<Pair<String, String>> select(String tableName, String primaryKeyColumnName, String columnName) {
    return jdbcTemplate.query(String.format("SELECT %s, %s FROM %s", primaryKeyColumnName, columnName, tableName),
        (rs, n) -> Pair.of(rs.getString(primaryKeyColumnName), rs.getString(columnName))
    );
  }
}
