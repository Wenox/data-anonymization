package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Worksheet;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorksheetRepository extends CrudRepository<Worksheet, String> {

  List<Worksheet> findAll();
}
