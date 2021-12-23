package com.wenox.anonymization.core.repository;

import com.wenox.anonymization.core.domain.ResetPasswordToken;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, UUID> {

  ResetPasswordToken findByToken(String token);
}
