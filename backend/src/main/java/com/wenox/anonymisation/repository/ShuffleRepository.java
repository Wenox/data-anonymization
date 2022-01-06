package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Shuffle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShuffleRepository extends CrudRepository<Shuffle, Long> {
}
