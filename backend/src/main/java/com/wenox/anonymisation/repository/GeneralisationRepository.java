package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Generalisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralisationRepository extends CrudRepository<Generalisation, Long> {
}
