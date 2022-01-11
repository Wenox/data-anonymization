package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.randomnumber.RandomNumber;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RandomNumberRepository extends CrudRepository<RandomNumber, Long> {
}
