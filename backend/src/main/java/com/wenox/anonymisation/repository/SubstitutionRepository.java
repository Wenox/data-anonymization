package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Substitution;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubstitutionRepository extends CrudRepository<Substitution, Long> {
}
