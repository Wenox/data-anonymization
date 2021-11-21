package com.wenox.anonymization.uploader.extractor;

import com.wenox.anonymization.core.service.ConnectionDetails;
import com.wenox.anonymization.core.service.DataSourceFactory;
import com.wenox.anonymization.uploader.extractor.metadata.Column;
import com.wenox.anonymization.uploader.extractor.metadata.ForeignKey;
import com.wenox.anonymization.uploader.extractor.metadata.PrimaryKey;
import com.wenox.anonymization.uploader.extractor.metadata.Schema;
import com.wenox.anonymization.uploader.extractor.metadata.Table;
import com.wenox.anonymization.uploader.extractor.metadata.TemplateMetadata;
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

  public TemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException {

    final DataSource dataSource = dataSourceFactory.getDataSource(connectionDetails);
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

    final DatabaseMetaData extractor = dataSource.getConnection().getMetaData();

    final TemplateMetadata metadata = new TemplateMetadata();
    int numberOfSchemas = 0;
    int numberOfTables = 0;

    final var schemas = extractor.getSchemas();

    while (schemas.next()) {
      String schemaName = schemas.getString("TABLE_SCHEM");

      Schema schema = new Schema();
      schema.setSchemaName(schemaName);

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
        table.setTableName(tableName);
        table.setSchemaName(schemaName);

        if (schema.getTables() == null) {
          schema.setTables(new HashMap<>());
        }
        schema.getTables().putIfAbsent(tableName, table);
        numberOfTables++;

        Integer numberOfRows = jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM %s.%s", schemaName, tableName), Integer.class);
        table.setNumberOfRows(numberOfRows);

        final var PKs = extractor.getPrimaryKeys(null, schemaName, tableName);
        while (PKs.next()) {
          if (table.getPrimaryKeys() == null) {
            table.setPrimaryKeys(new HashMap<>());
          }
          String columnName = PKs.getString("COLUMN_NAME");
          String primaryKeyName = PKs.getString("PK_NAME");
          PrimaryKey primaryKey = new PrimaryKey();
          primaryKey.setColumnName(columnName);
          primaryKey.setPrimaryKeyName(primaryKeyName);
          primaryKey.setType(null);
          table.getPrimaryKeys().putIfAbsent(primaryKeyName, primaryKey);
        }

        final var exportedKeys = extractor.getExportedKeys(null, schemaName, tableName);
        while (exportedKeys.next()) {
          ForeignKey exportedKey = new ForeignKey();
          exportedKey.setReferencedSchemaName(exportedKeys.getString("FKTABLE_SCHEM"));
          exportedKey.setReferencedTableName(exportedKeys.getString("FKTABLE_NAME"));
          exportedKey.setReferencedColumnName(exportedKeys.getString("FKCOLUMN_NAME"));
          exportedKey.setReferencingSchemaName(exportedKeys.getString("PKTABLE_SCHEM"));
          exportedKey.setReferencingTableName(exportedKeys.getString("PKTABLE_NAME"));
          exportedKey.setReferencingColumnName(exportedKeys.getString("PKCOLUMN_NAME"));
          String foreignKeyName = exportedKeys.getString("FK_NAME");
          exportedKey.setForeignKeyName(foreignKeyName);
          exportedKey.setPrimaryKeyName(exportedKeys.getString("PK_NAME"));
          exportedKey.setType(null); // todo
          if (table.getExportedKeys() == null) {
            table.setExportedKeys(new HashMap<>());
          }
          table.getExportedKeys().putIfAbsent(foreignKeyName, exportedKey);
        }

        final var importedKeys = extractor.getImportedKeys(null, schemaName, tableName);
        while (importedKeys.next()) {
          ForeignKey importedKey = new ForeignKey();
          importedKey.setReferencedSchemaName(importedKeys.getString("FKTABLE_SCHEM"));
          importedKey.setReferencedTableName(importedKeys.getString("FKTABLE_NAME"));
          importedKey.setReferencedColumnName(importedKeys.getString("FKCOLUMN_NAME"));
          importedKey.setReferencingSchemaName(importedKeys.getString("PKTABLE_SCHEM"));
          importedKey.setReferencingTableName(importedKeys.getString("PKTABLE_NAME"));
          importedKey.setReferencingColumnName(importedKeys.getString("PKCOLUMN_NAME"));
          importedKey.setForeignKeyName(importedKeys.getString("FK_NAME"));
          String primaryKeyName = importedKeys.getString("PK_NAME");
          importedKey.setPrimaryKeyName(primaryKeyName);
          importedKey.setType(null); // todo
          if (table.getImportedKeys() == null) {
            table.setImportedKeys(new HashMap<>());
          }
          table.getImportedKeys().putIfAbsent(primaryKeyName, importedKey);
        }

        final var columns = extractor.getColumns(null, schemaName, tableName, null);
        while (columns.next()) {

          String columnName = columns.getString("COLUMN_NAME");
          String columnSize = columns.getString("COLUMN_SIZE"); // todo: consider using it or remove it
          String datatype = columns.getString("DATA_TYPE");
          String isNullable = columns.getString("IS_NULLABLE");
          String isAutoIncrement = columns.getString("IS_AUTOINCREMENT"); // todo: consider using it or remove it

          Column column = new Column();
          column.setColumnName(columnName);
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
