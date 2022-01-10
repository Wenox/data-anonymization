package com.wenox.processing.service.query;

public class Query {

  private final QueryType queryType;

  private final String tableName;

  private final String primaryKeyType;
  private final String primaryKeyColumnName;
  private final String primaryKeyValue;

  private final String columnType;
  private final String columnName;
  private final String columnValue;

  public Query(QueryBuilder builder) {
    this.queryType = builder.queryType;
    this.tableName = builder.tableName;
    this.primaryKeyType = builder.primaryKeyType;
    this.primaryKeyColumnName = builder.primaryKeyColumnName;
    this.primaryKeyValue = builder.primaryKeyValue;
    this.columnType = builder.columnType;
    this.columnName = builder.columnName;
    this.columnValue = builder.columnValue;
  }
  
  @Override
  public String toString() {
    return switch (queryType) {
      case UPDATE -> createUpdateQuery();
      case ALTER_COLUMN_TYPE_TEXT -> createAlterColumnTypeToTextQuery();
      case ALTER_COLUMN_TYPE_INTEGER -> createAlterColumnTypeToIntegerQuery();
    };
  }

  public String createUpdateQuery() {
    var query = new StringBuilder();

    query.append("UPDATE ")
        .append(tableName)
        .append(" SET ")
        .append(columnName)
        .append(" = ");


    if (isQuotedType(columnType)) {
      query.append(String.format("'%s'", columnValue));
    } else {
      query.append(columnValue);
    }

    query.append(" WHERE ")
        .append(primaryKeyColumnName)
        .append(" = ");

    if (isQuotedType(primaryKeyType)) {
      query.append(String.format("'%s'", primaryKeyValue));
    } else {
      query.append(primaryKeyValue);
    }

    query.append(";\n");

    return query.toString();
  }

  public String createAlterColumnTypeToTextQuery() {
    return String.format(
        """
        
        ALTER TABLE %s ALTER COLUMN %s TYPE TEXT USING '';
        """, tableName, columnName);
  }

  public String createAlterColumnTypeToIntegerQuery() {
    return String.format(
        """
        
        ALTER TABLE %s ALTER COLUMN %s TYPE INTEGER USING 0;
        """, tableName, columnName);
  }

  private boolean isQuotedType(String type) {
    return switch (type) {
      case "12" -> true;
      case "4" -> false;
      default -> throw new IllegalStateException("Unsupported column type: " + type);
    };
  }

  public QueryType getQueryType() {
    return queryType;
  }

  public String getPrimaryKeyType() {
    return primaryKeyType;
  }

  public String getTableName() {
    return tableName;
  }

  public String getPrimaryKeyColumnName() {
    return primaryKeyColumnName;
  }

  public String getPrimaryKeyValue() {
    return primaryKeyValue;
  }

  public String getColumnType() {
    return columnType;
  }

  public String getColumnName() {
    return columnName;
  }

  public String getColumnValue() {
    return columnValue;
  }

  public static class QueryBuilder {

    private final QueryType queryType; // update

    private String tableName;

    private String primaryKeyType;
    private String primaryKeyColumnName;
    private String primaryKeyValue;

    private String columnType;
    private String columnName;
    private String columnValue;

    public QueryBuilder(QueryType queryType) {
      this.queryType = queryType;
    }

    public QueryBuilder tableName(String tableName) {
      this.tableName = tableName;
      return this;
    }

    public QueryBuilder primaryKeyType(String primaryKeyType) {
      this.primaryKeyType = primaryKeyType;
      return this;
    }

    public QueryBuilder primaryKeyColumnName(String primaryKeyColumnName) {
      this.primaryKeyColumnName = primaryKeyColumnName;
      return this;
    }

    public QueryBuilder primaryKeyValue(String primaryKeyValue) {
      this.primaryKeyValue = primaryKeyValue;
      return this;
    }

    public QueryBuilder columnType(String columnType) {
      this.columnType = columnType;
      return this;
    }

    public QueryBuilder columnName(String columnName) {
      this.columnName = columnName;
      return this;
    }

    public QueryBuilder columnValue(String columnValue) {
      this.columnValue = columnValue;
      return this;
    }

    public Query build() {
      return new Query(this);
    }
  }
}
