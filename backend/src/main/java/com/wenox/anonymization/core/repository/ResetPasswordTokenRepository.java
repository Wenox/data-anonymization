package com.wenox.anonymization.core.repository;

import com.wenox.anonymization.core.domain.ResetPasswordToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends CrudRepository<ResetPasswordToken, Long> {

  ResetPasswordToken findByToken(String token);
}
