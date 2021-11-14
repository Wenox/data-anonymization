package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.commons.ConnectionDetails;
import com.wenox.anonymization.commons.DataSourceFactory;
import com.wenox.anonymization.uploader.extractor.metadata.Column;
import com.wenox.anonymization.uploader.extractor.metadata.Schema;
import com.wenox.anonymization.uploader.extractor.metadata.Table;
import com.wenox.anonymization.uploader.extractor.metadata.WorksheetTemplateMetadata;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
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
}
