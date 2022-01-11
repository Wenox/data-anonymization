package com.wenox.processing.service.operations;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.processing.domain.Pair;
import com.wenox.processing.service.query.Query;
import com.wenox.processing.service.query.QueryType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Types;
import java.util.List;

public abstract class AnonymisationFacade {

  protected abstract List<Pair<String, String>> getAnonymisedRows(ColumnOperations operations,
                                                                  List<Pair<String, String>> rows);

  protected abstract AnonymisationService<?> getAnonymisationService();

  public void handleAnonymisation(ColumnOperations operations, List<Pair<String, String>> rows, Path fileLocation) {
    var anonymisedRows = getAnonymisedRows(operations, rows);
    if (anonymisedRows == null) {
      return;
    }

    var anonymisationService = getAnonymisationService();
    if (anonymisationService.altersTypeToText(operations.getColumnType())) {
      handleAlterTypeToText(operations, fileLocation);
    }
    if (anonymisationService.altersTypeToInteger(operations.getColumnType())) {
      handleAlterTypeToInteger(operations, fileLocation);
    }

    writeAnonymisationUpdatesToFile(operations, anonymisedRows, fileLocation);
  }

  protected void writeAnonymisationUpdatesToFile(ColumnOperations operations, List<Pair<String, String>> rows,
                                              Path fileLocation) {
    for (var row : rows) {
      try {
        Files.writeString(fileLocation,
            new Query.QueryBuilder(QueryType.UPDATE)
                .tableName(operations.getTableName())
                .primaryKeyColumnName(operations.getPrimaryKeyColumnName())
                .primaryKeyType(operations.getPrimaryKeyColumnType())
                .primaryKeyValue(row.getFirst())
                .columnName(operations.getColumnName())
                .columnType(operations.getColumnType())
                .columnValue(row.getSecond())
                .build()
                .toString(),
            StandardOpenOption.APPEND);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  protected void writeAnonymisationUpdatesToFileAsText(ColumnOperations operations, List<Pair<String, String>> rows, Path fileLocation) {
    for (var row : rows) {
      try {
        Files.writeString(fileLocation,
            new Query.QueryBuilder(QueryType.UPDATE)
                .tableName(operations.getTableName())
                .primaryKeyColumnName(operations.getPrimaryKeyColumnName())
                .primaryKeyType(operations.getPrimaryKeyColumnType())
                .primaryKeyValue(row.getFirst())
                .columnName(operations.getColumnName())
                .columnType(String.valueOf(Types.VARCHAR))
                .columnValue(row.getSecond())
                .build()
                .toString(),
            StandardOpenOption.APPEND);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  protected void writeAnonymisationUpdatesToFileAsInteger(ColumnOperations operations, List<Pair<String, String>> rows, Path fileLocation) {
    for (var row : rows) {
      try {
        Files.writeString(fileLocation,
            new Query.QueryBuilder(QueryType.UPDATE)
                .tableName(operations.getTableName())
                .primaryKeyColumnName(operations.getPrimaryKeyColumnName())
                .primaryKeyType(operations.getPrimaryKeyColumnType())
                .primaryKeyValue(row.getFirst())
                .columnName(operations.getColumnName())
                .columnType(String.valueOf(Types.INTEGER))
                .columnValue(row.getSecond())
                .build()
                .toString(),
            StandardOpenOption.APPEND);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  protected void handleAlterTypeToText(ColumnOperations operations, Path fileLocation) {
    changeColumnType(operations, fileLocation, QueryType.ALTER_COLUMN_TYPE_TEXT);
  }

  protected void handleAlterTypeToInteger(ColumnOperations operations, Path fileLocation) {
    changeColumnType(operations, fileLocation, QueryType.ALTER_COLUMN_TYPE_INTEGER);
  }

  private void changeColumnType(ColumnOperations operations, Path fileLocation, QueryType queryType) {
    try {
      Files.writeString(fileLocation,
          new Query.QueryBuilder(queryType)
              .tableName(operations.getTableName())
              .columnName(operations.getColumnName())
              .build()
              .toString(),
          StandardOpenOption.APPEND);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}