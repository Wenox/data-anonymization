package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.perturbation.Perturbation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerturbationRepository extends CrudRepository<Perturbation, Long> {
}
