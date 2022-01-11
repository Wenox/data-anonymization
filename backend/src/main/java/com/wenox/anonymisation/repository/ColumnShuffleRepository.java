package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.columnshuffle.ColumnShuffle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnShuffleRepository extends CrudRepository<ColumnShuffle, Long> {
}
