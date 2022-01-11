package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.shortening.Shortening;
import org.springframework.data.repository.CrudRepository;

public interface ShorteningRepository extends CrudRepository<Shortening, Long> {
}
