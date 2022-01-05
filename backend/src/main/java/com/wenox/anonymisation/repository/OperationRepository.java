package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Operation;
import com.wenox.anonymisation.domain.Worksheet;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, String> {

  List<Operation> findAll();

  List<Operation> findAllByWorksheetAndTableName(Worksheet worksheet, String tableName);
}