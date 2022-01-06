package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Operation;
import com.wenox.anonymisation.domain.Worksheet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {

  List<Operation> findAll();

  List<Operation> findAllByWorksheetAndTableName(Worksheet worksheet, String tableName);

  default Optional<Operation> findOperationForColumn(Worksheet worksheet, String tableName, String columnName) {
    return findByWorksheetAndTableNameAndColumnName(worksheet, tableName, columnName);
  }

  Optional<Operation> findByWorksheetAndTableNameAndColumnName(Worksheet worksheet, String tableName, String columnName);
}