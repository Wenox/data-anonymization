package com.wenox.uploading.extractor;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.uploading.extractor.domain.metadata.Column;
import com.wenox.uploading.extractor.domain.metadata.PrimaryKey;
import com.wenox.uploading.extractor.domain.metadata.Table;
import com.wenox.uploading.extractor.domain.metadata.TemplateMetadata;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMetadataExtractor implements MetadataExtractor {

  private final DataSourceFactory dataSourceFactory;

  public DatabaseMetadataExtractor(final DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }

  public TemplateMetadata extractMetadata(DatabaseConnection databaseConnection) throws SQLException {

    final DataSource dataSource = dataSourceFactory.getDataSource(databaseConnection);
    final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    final DatabaseMetaData extractor = dataSource.getConnection().getMetaData();
    final TemplateMetadata metadata = new TemplateMetadata();

    final ResultSet tables = extractor.getTables(null, "public", null, new String[] {"TABLE"});
    while (tables.next()) {
      String tableName = tables.getString("TABLE_NAME");
      Integer numberOfRows = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM " + tableName, Integer.class);
      Integer numberOfColumns =
          jdbcTemplate.queryForObject(getQueryForNumberOfColumnsInTable(tableName), Integer.class);
      Table table = new Table(tableName, numberOfRows, numberOfColumns);
      metadata.insertTable(table);

      final var PKs = extractor.getPrimaryKeys(null, "public", tableName);
      while (PKs.next()) {
        String columnName = PKs.getString("COLUMN_NAME");
        String primaryKeyName = PKs.getString("PK_NAME");
        final var pkColumnResultSet = extractor.getColumns(null, "public", tableName, columnName);
        String type = "";
        if (pkColumnResultSet.next()) {
          type = pkColumnResultSet.getString("DATA_TYPE");
        }
        PrimaryKey primaryKey = new PrimaryKey(columnName, type, primaryKeyName);
        table.setPrimaryKey(primaryKey);
      }

//      final var exportedKeys = extractor.getExportedKeys(null, "public", tableName);
//      while (exportedKeys.next()) {
//        foreignKeyColumns.add(exportedKeys.getString("FKCOLUMN_NAME"));
//        foreignKeyColumns.add(exportedKeys.getString("PKCOLUMN_NAME"));;
//      }

      final Set<String> foreignKeyColumns = new HashSet<>();
      final var importedKeys = extractor.getImportedKeys(null, "public", tableName);
      while (importedKeys.next()) {
        foreignKeyColumns.add(importedKeys.getString("FKCOLUMN_NAME"));
      }

      final ResultSet columns = extractor.getColumns(null, "public", tableName, null);
      while (columns.next()) {
        String columnName = columns.getString("COLUMN_NAME");
        String type = columns.getString("DATA_TYPE");
        String isNullable = columns.getString("IS_NULLABLE");
        Column column = new Column(columnName, type, isNullable, table.getPrimaryKey().getColumnName().equals(columnName), foreignKeyColumns.contains(columnName));
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
