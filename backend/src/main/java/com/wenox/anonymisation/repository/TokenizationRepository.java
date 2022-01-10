package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.Tokenization;
import org.springframework.data.repository.CrudRepository;

public interface TokenizationRepository extends CrudRepository<Tokenization, Long> {
}
