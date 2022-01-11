package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.rowshuffle.RowShuffle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RowShuffleRepository extends CrudRepository<RowShuffle, Long> {
}
