package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Hashing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashingRepository extends CrudRepository<Hashing, Long> {
}
