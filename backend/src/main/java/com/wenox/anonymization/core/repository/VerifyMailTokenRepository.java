package com.wenox.anonymization.core.repository;

import com.wenox.anonymization.core.domain.VerifyMailToken;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface VerifyMailTokenRepository extends CrudRepository<VerifyMailToken, UUID> {

  VerifyMailToken findByToken(String token);
}

