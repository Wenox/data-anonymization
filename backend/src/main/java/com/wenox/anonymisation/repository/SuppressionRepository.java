package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Suppression;
import org.springframework.data.repository.CrudRepository;

public interface SuppressionRepository extends CrudRepository<Suppression, Long> {
}
