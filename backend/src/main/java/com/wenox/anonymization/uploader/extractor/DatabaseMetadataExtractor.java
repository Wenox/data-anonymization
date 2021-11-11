package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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


    final var tables = extractor.getTables(null, null, null, new String[]{"TABLE"});
    while (tables.next()) {

      String tableName = tables.getString("TABLE_NAME");
      String schemaName = tables.getString("TABLE_SCHEM");
      String type = tables.getString("TABLE_TYPE");
      String remarks = tables.getString("REMARKS");
      System.out.println("tableName: " + tableName);
      System.out.println("schema: " + schemaName);
      System.out.println("type: " + type);
      System.out.println("remarks: " + remarks);

      Table table = new Table();
      table.setName(tableName);
      table.setSchema(schemaName);

      final var columns = extractor.getColumns(null, schemaName, tableName, null);
      while (columns.next()) {

        String columnName = columns.getString("COLUMN_NAME");
        String columnSize = columns.getString("COLUMN_SIZE");
        String datatype = columns.getString("DATA_TYPE");
        String isNullable = columns.getString("IS_NULLABLE");
        String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");

        System.out.println("columnName: " + columnName);
        System.out.println("columnSize: " + columnSize);
        System.out.println("datatype: " + datatype);
        System.out.println("isNullable: " + isNullable);
        System.out.println("isAutoIncrement: " + isAutoIncrement);

        Column column = new Column();
        column.setName(columnName);
        column.setType(datatype);
        column.setNullable(isNullable);

        if (table.getColumns() == null) {
          table.setColumns(new ArrayList<>());
        }
        table.getColumns().add(column);

      }

      metadata.getTables().add(table);
    }

    System.out.println(metadata);

    return metadata;
  }

  static class WorksheetTemplateMetadata {
    List<Table> tables = new ArrayList<>();
    int numberOfTables;

    public List<Table> getTables() {
      return tables;
    }

    public void setTables(List<Table> tables) {
      this.tables = tables;
    }

    public int getNumberOfTables() {
      return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) {
      this.numberOfTables = numberOfTables;
    }
  }

  static class Table {
    String name;
    String schema;
    PK primaryKey;
    List<FK> foreignKeys;
    List<Column> columns;
    int numberOfRows;

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

    public List<FK> getForeignKeys() {
      return foreignKeys;
    }

    public void setForeignKeys(
        List<FK> foreignKeys) {
      this.foreignKeys = foreignKeys;
    }

    public List<Column> getColumns() {
      return columns;
    }

    public void setColumns(List<Column> columns) {
      this.columns = columns;
    }

    public int getNumberOfRows() {
      return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
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
