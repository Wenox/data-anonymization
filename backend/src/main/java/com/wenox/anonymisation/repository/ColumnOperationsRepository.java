package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.ColumnOperations;
import com.wenox.anonymisation.domain.Worksheet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnOperationsRepository extends CrudRepository<ColumnOperations, Long> {

  List<ColumnOperations> findAll();

  List<ColumnOperations> findAllByWorksheetAndTableName(Worksheet worksheet, String tableName);

  default Optional<ColumnOperations> findOperationForColumn(Worksheet worksheet, String tableName, String columnName) {
    return findByWorksheetAndTableNameAndColumnName(worksheet, tableName, columnName);
  }

  Optional<ColumnOperations> findByWorksheetAndTableNameAndColumnName(Worksheet worksheet, String tableName, String columnName);
}