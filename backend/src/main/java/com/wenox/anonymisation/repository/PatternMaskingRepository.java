package com.wenox.anonymisation.repository;

import com.wenox.anonymisation.domain.patternmasking.PatternMasking;
import org.springframework.data.repository.CrudRepository;

public interface PatternMaskingRepository extends CrudRepository<PatternMasking, Long> {
}
