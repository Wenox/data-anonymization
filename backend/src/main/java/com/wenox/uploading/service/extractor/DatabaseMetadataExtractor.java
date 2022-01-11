package com.wenox.uploading.service.extractor;

import com.wenox.infrastructure.service.DatabaseConnection;
import com.wenox.infrastructure.service.DataSourceFactory;
import com.wenox.uploading.domain.metadata.Column;
import com.wenox.uploading.domain.metadata.PrimaryKey;
import com.wenox.uploading.domain.metadata.Table;
import com.wenox.uploading.domain.metadata.TemplateMetadata;
import com.wenox.uploading.service.listeners.DatabaseRestoredListener;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMetadataExtractor implements MetadataExtractor {

  private final DataSourceFactory dataSourceFactory;

  private static final Logger log = LoggerFactory.getLogger(DatabaseMetadataExtractor.class);

  public DatabaseMetadataExtractor(DataSourceFactory dataSourceFactory) {
    this.dataSourceFactory = dataSourceFactory;
  }

  public TemplateMetadata extractMetadata(DatabaseConnection databaseConnection) throws SQLException {

    log.info("Starting to extract metadata...");

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

    log.info("Metadata extracted successfully!");
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
