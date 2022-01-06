package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Suppression;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuppressionRepository extends CrudRepository<Suppression, Long> {
}
