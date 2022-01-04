package com.wenox.uploading.extractor;

import com.wenox.infrastructure.service.ConnectionDetails;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.uploading.extractor.domain.metadata.Table;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
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

  public TemplateMetadata extractMetadata(ConnectionDetails connectionDetails) throws SQLException {

    final DataSource dataSource = dataSourceFactory.getDataSource(connectionDetails);
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    final DatabaseMetaData extractor = dataSource.getConnection().getMetaData();
    final TemplateMetadata metadata = new TemplateMetadata();

    final ResultSet tables = extractor.getTables(null, "public", null, new String[] {"TABLE"});
    while (tables.next()) {
      String tableName = tables.getString("TABLE_NAME");
      Integer numberOfRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
      Integer numberOfColumns = jdbcTemplate.queryForObject(getQueryForNumberOfColumnsInTable(tableName), Integer.class);
      Table table = new Table(tableName, numberOfRows, numberOfColumns);
      metadata.insertTable(table);

      final ResultSet columns = extractor.getColumns(null, "public", tableName, null);
      while (columns.next()) {
        String columnName = columns.getString("COLUMN_NAME");
        String type = columns.getString("DATA_TYPE");
        String isNullable = columns.getString("IS_NULLABLE");
        Column column = new Column(columnName, type, isNullable);
        table.insertColumn(column);
      }
    }

    return metadata;
  }

  private String getQueryForNumberOfColumnsInTable(String tableName) {
    return String.format(
        """
        SELECT count(*)
        FROM information_schema.columns
        WHERE table_name = '%s'
        """, tableName);
  }
}


//      final var PKs = extractor.getPrimaryKeys(null, schemaName, tableName);
//      while (PKs.next()) {
//        if (table.getPrimaryKeys() == null) {
//          table.setPrimaryKeys(new HashMap<>());
//        }
//        String columnName = PKs.getString("COLUMN_NAME");
//        String primaryKeyName = PKs.getString("PK_NAME");
//        PrimaryKey primaryKey = new PrimaryKey();
//        primaryKey.setColumnName(columnName);
//        primaryKey.setPrimaryKeyName(primaryKeyName);
//        primaryKey.setType(null);
//        table.getPrimaryKeys().putIfAbsent(primaryKeyName, primaryKey);
//      }
//
//      final var exportedKeys = extractor.getExportedKeys(null, schemaName, tableName);
//      while (exportedKeys.next()) {
//        ForeignKey exportedKey = new ForeignKey();
//        exportedKey.setReferencedSchemaName(exportedKeys.getString("FKTABLE_SCHEM"));
//        exportedKey.setReferencedTableName(exportedKeys.getString("FKTABLE_NAME"));
//        exportedKey.setReferencedColumnName(exportedKeys.getString("FKCOLUMN_NAME"));
//        exportedKey.setReferencingSchemaName(exportedKeys.getString("PKTABLE_SCHEM"));
//        exportedKey.setReferencingTableName(exportedKeys.getString("PKTABLE_NAME"));
//        exportedKey.setReferencingColumnName(exportedKeys.getString("PKCOLUMN_NAME"));
//        String foreignKeyName = exportedKeys.getString("FK_NAME");
//        exportedKey.setForeignKeyName(foreignKeyName);
//        exportedKey.setPrimaryKeyName(exportedKeys.getString("PK_NAME"));
//        exportedKey.setType(null); // todo
//        if (table.getExportedKeys() == null) {
//          table.setExportedKeys(new HashMap<>());
//        }
//        table.getExportedKeys().putIfAbsent(foreignKeyName, exportedKey);
//      }
//
//      final var importedKeys = extractor.getImportedKeys(null, schemaName, tableName);
//      while (importedKeys.next()) {
//        ForeignKey importedKey = new ForeignKey();
//        importedKey.setReferencedSchemaName(importedKeys.getString("FKTABLE_SCHEM"));
//        importedKey.setReferencedTableName(importedKeys.getString("FKTABLE_NAME"));
//        importedKey.setReferencedColumnName(importedKeys.getString("FKCOLUMN_NAME"));
//        importedKey.setReferencingSchemaName(importedKeys.getString("PKTABLE_SCHEM"));
//        importedKey.setReferencingTableName(importedKeys.getString("PKTABLE_NAME"));
//        importedKey.setReferencingColumnName(importedKeys.getString("PKCOLUMN_NAME"));
//        importedKey.setForeignKeyName(importedKeys.getString("FK_NAME"));
//        String primaryKeyName = importedKeys.getString("PK_NAME");
//        importedKey.setPrimaryKeyName(primaryKeyName);
//        importedKey.setType(null); // todo
//        if (table.getImportedKeys() == null) {
//          table.setImportedKeys(new HashMap<>());
//        }
//        table.getImportedKeys().putIfAbsent(primaryKeyName, importedKey);
//      }
