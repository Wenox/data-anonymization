package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMetadataExtractor implements MetadataExtractor {

  private final DataSourceFactory dataSourceFactory;

  public DatabaseMetadataExtractor(final DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }

  public WorksheetTemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException {

    final DataSource dataSource = dataSourceFactory.getDataSource(connectionDetails);
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    final DatabaseMetaData extractor = dataSource.getConnection().getMetaData();

    final WorksheetTemplateMetadata metadata = new WorksheetTemplateMetadata();
    int numberOfSchemas = 0;
    int numberOfTables = 0;

    final var schemas = extractor.getSchemas();

    while (schemas.next()) {
      String schemaName = schemas.getString("TABLE_SCHEM");

      Schema schema = new Schema();
      schema.setName(schemaName);

      if (metadata.getSchemas() == null) {
        metadata.setSchemas(new HashMap<>());
      }
      metadata.getSchemas().putIfAbsent(schemaName, schema);
      numberOfSchemas++;

      final var tables = extractor.getTables(null, schemaName, null, new String[]{"TABLE"});
      while (tables.next()) {

        String tableName = tables.getString("TABLE_NAME");
        String type = tables.getString("TABLE_TYPE"); /// todo: consider using it or remove it
        String remarks = tables.getString("REMARKS"); /// todo: consider using it or remove it

        Table table = new Table();
        table.setName(tableName);
        table.setSchema(schemaName);

        if (schema.getTables() == null) {
          schema.setTables(new HashMap<>());
        }
        schema.getTables().putIfAbsent(tableName, table);
        numberOfTables++;

        Integer numberOfRows = jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM %s.%s", schemaName, tableName), Integer.class);
        table.setNumberOfRows(numberOfRows);

        final var columns = extractor.getColumns(null, schemaName, tableName, null);
        while (columns.next()) {

          String columnName = columns.getString("COLUMN_NAME");
          String columnSize = columns.getString("COLUMN_SIZE"); // todo: consider using it or remove it
          String datatype = columns.getString("DATA_TYPE");
          String isNullable = columns.getString("IS_NULLABLE");
          String isAutoIncrement = columns.getString("IS_AUTOINCREMENT"); // todo: consider using it or remove it

          Column column = new Column();
          column.setName(columnName);
          column.setType(datatype);
          column.setNullable(isNullable);

          if (table.getColumns() == null) {
            table.setColumns(new HashMap<>());
          }
          table.getColumns().putIfAbsent(columnName, column);

        }
      }
    }

    metadata.setNumberOfSchemas(numberOfSchemas);
    metadata.setNumberOfTables(numberOfTables);

    return metadata;
  }

  static class WorksheetTemplateMetadata {
    Map<String, Schema> schemas = new HashMap<>();
    int numberOfSchemas;
    int numberOfTables;

    public Map<String, Schema> getSchemas() {
      return schemas;
    }

    public void setSchemas(
        Map<String, Schema> schemas) {
      this.schemas = schemas;
    }

    public int getNumberOfSchemas() {
      return numberOfSchemas;
    }

    public void setNumberOfSchemas(int numberOfSchemas) {
      this.numberOfSchemas = numberOfSchemas;
    }

    public int getNumberOfTables() {
      return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
      this.numberOfTables = numberOfTables;
    }
  }

  static class Schema {
    String name;
    Map<String, Table> tables;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public Map<String, Table> getTables() {
      return tables;
    }

    public void setTables(
        Map<String, Table> tables) {
      this.tables = tables;
    }
  }

  static class Table {
    String name;
    String schema;
    PK primaryKey;
    Map<String, FK> foreignKeys;
    Map<String, Column> columns;
    Integer numberOfRows;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getSchema() {
      return schema;
    }

    public void setSchema(String schema) {
      this.schema = schema;
    }

    public PK getPrimaryKey() {
      return primaryKey;
    }

    public void setPrimaryKey(PK primaryKey) {
      this.primaryKey = primaryKey;
    }

    public Map<String, FK> getForeignKeys() {
      return foreignKeys;
    }

    public void setForeignKeys(
        Map<String, FK> foreignKeys) {
      this.foreignKeys = foreignKeys;
    }

    public Map<String, Column> getColumns() {
      return columns;
    }

    public void setColumns(
        Map<String, Column> columns) {
      this.columns = columns;
    }

    public Integer getNumberOfRows() {
      return numberOfRows;
    }

    public void setNumberOfRows(Integer numberOfRows) {
      this.numberOfRows = numberOfRows;
    }
  }

  static class PK {
    String name;
    String type;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }
  }

  static class FK {
    String name;
    String type;
    String referencedColumn;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String getReferencedColumn() {
      return referencedColumn;
    }

    public void setReferencedColumn(String referencedColumn) {
      this.referencedColumn = referencedColumn;
    }
  }

  static class Column {
    String name;
    String type; // todo: enum
    String nullable;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public String isNullable() {
      return nullable;
    }

    public void setNullable(String nullable) {
      this.nullable = nullable;
    }
  }
}
